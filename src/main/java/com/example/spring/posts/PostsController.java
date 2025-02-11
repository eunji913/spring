package com.example.spring.posts;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/posts")
public class PostsController {
    
    @Autowired
    PostsService postsService;

    private final String uploadPath = "C:/uploads/board";
    private static final Logger logger = LoggerFactory.getLogger(PostsController.class); // logger 선언 추가

    // 게시글 등록
    @GetMapping("/create")
    public ModelAndView createGet() {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("posts/create");
        return mav;
    }

    @PostMapping("/create")
    public ModelAndView createPost(PostsVo postsVo, HttpServletRequest request, RedirectAttributes redirectAttributes) {
        ModelAndView mav = new ModelAndView();
    
        try {
            // 파일 업로드 처리
            MultipartFile uploadFile = postsVo.getUploadFile();
            if (uploadFile != null && !uploadFile.isEmpty()) {
                String originalFileName = uploadFile.getOriginalFilename();
                String fileName = UUID.randomUUID().toString() + "_" + originalFileName;
    
                // 업로드 디렉토리가 없으면 생성
                File uploadDir = new File(uploadPath);
                if (!uploadDir.exists()) {
                    uploadDir.mkdirs();
                }
    
                // 파일 저장
                File destFile = new File(uploadPath + File.separator + fileName);
                uploadFile.transferTo(destFile);
    
                // 파일 정보 설정
                postsVo.setFileName(fileName);
                postsVo.setOriginalFileName(originalFileName);
            }
    
            // 로그인 사용자 ID
            String userId = (String) request.getSession().getAttribute("userId");
            postsVo.setUsername(userId);
    
            // 게시글 저장
            boolean created = postsService.create(postsVo);
    
            if (created) {
                redirectAttributes.addFlashAttribute("successMessage", "게시글이 등록되었습니다.");
                mav.setViewName("redirect:/posts/");
            } else {
                redirectAttributes.addFlashAttribute("errorMessage", "게시글 등록에 실패했습니다.");
                mav.setViewName("redirect:/posts/create");
            }
        } catch (IOException e) { // IOException만 별도로 처리
            logger.error("IOException occurred during post creation: ", e);
            redirectAttributes.addFlashAttribute("errorMessage", "파일 업로드 중 오류가 발생했습니다.");
            mav.setViewName("redirect:/posts/create");
        } catch (Exception e) { // 나머지 예외는 Exception으로 처리
            logger.error("Unexpected error occurred during post creation: ", e);
            redirectAttributes.addFlashAttribute("errorMessage", "예상치 못한 오류가 발생했습니다.");
            mav.setViewName("redirect:/posts/create");
        }
    
        return mav;
    }

    // 게시글 목록
    @GetMapping("/")
    public ModelAndView listGet(
        @RequestParam(value = "page", defaultValue = "1") int page,
        @RequestParam(required = false) String searchType,
        @RequestParam(required = false) String searchKeyword
    ) {
        ModelAndView mav = new ModelAndView();
        int pageSize = 10; // 페이지당 게시글 수
        Map<String, Object> result = postsService.list(page, pageSize, searchType, searchKeyword);
        mav.addObject("postsVoList", result.get("postsVoList"));
        mav.addObject("pagination", result.get("pagination"));
        mav.addObject("searchType", result.get("searchType"));
        mav.addObject("searchKeyword", result.get("searchKeyword"));
        mav.setViewName("posts/list");
        return mav;
    }

    // 게시글 보기
    @GetMapping("/{id}")
    public ModelAndView readGet(@PathVariable("id") int id) {
        ModelAndView mav = new ModelAndView();
        PostsVo postsVo = postsService.read(id);
        mav.addObject("postsVo", postsVo);
        mav.setViewName("posts/read");
        return mav;
    }

    // 게시글 수정
    @GetMapping("/{id}/update")
    public ModelAndView updateGet(@PathVariable("id") int id) {
        ModelAndView mav = new ModelAndView();
        PostsVo postsVo = postsService.read(id);
        mav.addObject("postsVo", postsVo);
        mav.setViewName("posts/update");
        return mav;
    }

    @PostMapping("/{id}/update")
    public ModelAndView updatePost(@PathVariable("id") int id, PostsVo postsVo, RedirectAttributes redirectAttributes) {
        ModelAndView mav = new ModelAndView();
    
        try {
            // 기존 게시글 정보 조회
            PostsVo existingPostsVo = postsService.read(postsVo.getId());
            if (existingPostsVo == null) {
                redirectAttributes.addFlashAttribute("errorMessage", "게시글을 찾을 수 없습니다.");
                mav.setViewName("redirect:/posts/");
                return mav;
            }
    
            // 파일 처리
            MultipartFile uploadFile = postsVo.getUploadFile();
            String existingFileName = existingPostsVo.getFileName();
    
            // 기존 파일 삭제 처리
            if (postsVo.isDeleteFile() || (uploadFile != null && !uploadFile.isEmpty())) {
                if (existingFileName != null) {
                    File fileToDelete = new File(uploadPath + File.separator + existingFileName);
                    if (fileToDelete.exists()) {
                        fileToDelete.delete();
                    }
                    // 파일 정보 초기화
                    postsVo.setFileName(null);
                    postsVo.setOriginalFileName(null);
                }
            } else {
                // 파일을 삭제하지 않고 유지하는 경우
                postsVo.setFileName(existingFileName);
                postsVo.setOriginalFileName(existingPostsVo.getOriginalFileName());
            }
    
            // 새 파일 업로드 처리
            if (uploadFile != null && !uploadFile.isEmpty()) {
                String originalFileName = uploadFile.getOriginalFilename();
                String fileName = UUID.randomUUID().toString() + "_" + originalFileName;
    
                // 업로드 디렉토리가 없으면 생성
                File uploadDir = new File(uploadPath);
                if (!uploadDir.exists()) {
                    uploadDir.mkdirs();
                }
    
                // 파일 저장
                File destFile = new File(uploadPath + File.separator + fileName);
                uploadFile.transferTo(destFile);
    
                // 파일 정보 설정
                postsVo.setFileName(fileName);
                postsVo.setOriginalFileName(originalFileName);
            }
    
            // 게시글 수정
            boolean updated = postsService.update(postsVo);
            if (updated) {
                redirectAttributes.addFlashAttribute("successMessage", "게시글이 수정되었습니다.");
                mav.setViewName("redirect:/posts/" + id);
            } else {
                redirectAttributes.addFlashAttribute("errorMessage", "게시글 수정에 실패했습니다.");
                mav.setViewName("redirect:/posts/" + id + "/update");
            }
        } catch (IOException e) {
            // IOException을 별도로 처리
            logger.error("IOException occurred during post update: ", e);
            redirectAttributes.addFlashAttribute("errorMessage", "파일 업로드 중 오류가 발생했습니다.");
            mav.setViewName("redirect:/posts/" + id + "/update");
        } catch (Exception e) {
            // 나머지 예외는 Exception으로 처리
            logger.error("Unexpected error occurred during post update: ", e);
            redirectAttributes.addFlashAttribute("errorMessage", "게시글 수정 중 오류가 발생했습니다.");
            mav.setViewName("redirect:/posts/" + id + "/update");
        }
    
        return mav;
    }
    

    // 게시글 삭제
    @PostMapping("/{id}/delete")
    public ModelAndView deletePost(@PathVariable("id") int id, PostsVo postsVo, RedirectAttributes redirectAttributes) {
        ModelAndView mav = new ModelAndView();

        try {
            // 게시글 정보 조회
            PostsVo existingPostsVo = postsService.read(id);
            if (existingPostsVo == null) {
                redirectAttributes.addFlashAttribute("errorMessage", "게시글을 찾을 수 없습니다.");
                mav.setViewName("redirect:/posts/");
                return mav;
            }

            // 게시글 삭제
            boolean deleted = postsService.delete(postsVo);
            if (deleted) {
                // 파일 삭제
                if (existingPostsVo.getFileName() != null) {
                    File fileToDelete = new File(uploadPath + File.separator + existingPostsVo.getFileName());
                    if (fileToDelete.exists()) {
                        fileToDelete.delete();
                    }
                }
                redirectAttributes.addFlashAttribute("successMessage", "게시글이 삭제되었습니다.");
                mav.setViewName("redirect:/posts/");
            } else {
                redirectAttributes.addFlashAttribute("errorMessage", "게시글 삭제에 실패했습니다.");
                mav.setViewName("redirect:/posts/" + id);
            }
        } catch (Exception e) {
            logger.error("Error occurred during post deletion: ", e);
            redirectAttributes.addFlashAttribute("errorMessage", "게시글 삭제 중 오류가 발생했습니다.");
            mav.setViewName("redirect:/posts/" + id);
        }

        return mav;
    }

    // 파일 다운로드
    @GetMapping("/{id}/download")
    public ResponseEntity<Resource> downloadFile(@PathVariable("id") int id) {
        try {
            // 게시글 정보 조회
            PostsVo postsVo = postsService.read(id);
            if (postsVo == null || postsVo.getFileName() == null) {
                return ResponseEntity.notFound().build();
            }

            // 파일 경로 생성
            Path filePath = Paths.get(uploadPath).resolve(postsVo.getFileName());
            Resource resource = new UrlResource(filePath.toUri());

            // 파일이 존재하고 읽을 수 있는지 확인
            if (!resource.exists() || !resource.isReadable()) {
                return ResponseEntity.notFound().build();
            }

            // 다운로드될 파일명 설정 (원본 파일명 사용)
            String downloadName = postsVo.getOriginalFileName();

            // 한글 파일명 처리
            String encodedDownloadName = new String(downloadName.getBytes("UTF-8"), "ISO-8859-1");

            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + encodedDownloadName + "\"")
                    .body(resource);

        } catch (Exception e) {
            logger.error("Error occurred during file download: ", e);
            return ResponseEntity.internalServerError().build();
        }
    }
}