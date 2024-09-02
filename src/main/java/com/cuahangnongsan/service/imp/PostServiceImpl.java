package com.cuahangnongsan.service.imp;

import com.cuahangnongsan.dto.request.PostRequest;
import com.cuahangnongsan.entity.Post;
import com.cuahangnongsan.entity.User;
import com.cuahangnongsan.mapper.PostMapper;
import com.cuahangnongsan.repository.PostRepository;
import com.cuahangnongsan.service.IPostService;
import com.cuahangnongsan.util.ProcessImage;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.awt.image.ImagingOpException;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import com.cuahangnongsan.dto.response.*;
@Service
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PostServiceImpl implements IPostService {

    @Autowired
    PostRepository postRepository;

    @Autowired
    PostMapper postMapper;

    @PreAuthorize("hasAuthority('AUTHORITY_CREATE_POST')")
    @Override
    public PostResponse save(String id, MultipartFile image, PostRequest request, RedirectAttributes redirectAttributes, User user) throws IOException {

        String imageName  = "";
        Post oldPost = null;
        Post post = postMapper.toPost(request);
        post.setId(id);

        if(image != null){
            if(Objects.equals(image.getOriginalFilename(), "")
                    || request.getContent() == null){
                redirectAttributes.addFlashAttribute("post",
                        postMapper.toPostResponse(post));

                redirectAttributes.addFlashAttribute("errorImage",
                        Objects.equals(image.getOriginalFilename(), "") ? "Vui lòng chọn ảnh!": null);

                redirectAttributes.addFlashAttribute("errorContent",
                        request.getContent() == null ? "Vui lòng nhập nội dung!": null);
                throw new ImagingOpException("Image not found!");
            }else {
                imageName = ProcessImage.upload(image,"/posts");
            }
        }else {
            redirectAttributes.addFlashAttribute("post",
                    postMapper.toPostResponse(post));
            redirectAttributes.addFlashAttribute("errorImage",
                    "Vui lòng chọn ảnh!");
            throw new ImagingOpException("Image not found!");
        }

        post.setCreatedTime(LocalDateTime.now());

        if(id != null){
            oldPost  = findById(id);
            imageName = !Objects.equals(image.getOriginalFilename(), "")
                    ? ProcessImage.upload(image,"/posts")
                    : oldPost.getImage();
            post.setCreatedTime(oldPost.getCreatedTime());
        }

        post.setImage(imageName);
        post.setModifiedTime(LocalDateTime.now());
        post.setUser(user);

        return postMapper.toPostResponse( postRepository.save(post));
    }

    @PreAuthorize("hasAuthority('AUTHORITY_DELETE_POST')")
    @Override
    public void delete(Post post) {
        postRepository.delete(post);
    }

    @Override
    public List<Post> findAll() {
        return postRepository.findAll();
    }


    @Override
    public Post findById(String id) {
        return postRepository.findById(id).orElseThrow();
    }
}
