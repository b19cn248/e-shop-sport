package com.shopsport.server.controller;

import com.shopsport.common.entity.Role;
import com.shopsport.common.entity.User;
import com.shopsport.server.exception.user.UserNotFoundException;
import com.shopsport.server.service.RoleService;
import com.shopsport.server.service.UserService;
import com.shopsport.server.utils.FileUploadUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

import static com.shopsport.server.constant.CommonConstants.UserConstants.*;

@Controller
@RequiredArgsConstructor
@Slf4j
public class UserController {

  private final UserService userService;
  private final RoleService roleService;

  @GetMapping("/users")
  public String listAll(Model model) {
    List<User> users = userService.getAll();

    model.addAttribute("users", users);

    return listByPage(1, model, "firstName", "asc", null);
  }

  @GetMapping("/users/page/{pageNum}")
  public String listByPage(
        @PathVariable(name = "pageNum") int pageNum,
        Model model,
        @Param("sortField") String sortField,
        @Param("sortDir") String sortDir,
        @Param("keyword") String keyword
  ) {
    log.info("(listAll) pageNum:{}", pageNum);
    log.info("Sort Field: {}", sortField);
    log.info("Sort Dir: {}", sortDir);

    List<User> users = userService.list(pageNum, USER_PER_PAGE, sortField, sortDir, keyword);
    log.info("(listByPage) users: {}", users);

    int totalItems = (Objects.isNull(keyword)) ? userService.count() : userService.countByKeyword(keyword);
    long startCount = (long) (pageNum - 1) * USER_PER_PAGE + 1;
    long endCount = startCount + USER_PER_PAGE - 1;

    if (endCount > totalItems) {
      endCount = totalItems;
    }

    int totalPages = (totalItems % USER_PER_PAGE == 0) ? totalItems / USER_PER_PAGE : totalItems / USER_PER_PAGE + 1;

    String reverseSortDir = sortDir.equals("asc") ? "desc" : "asc";

    model.addAttribute("startCount", startCount);
    model.addAttribute("totalPages", totalPages);
    model.addAttribute("currentPage", pageNum);
    model.addAttribute("endCount", endCount);
    model.addAttribute("totalItems", totalItems);
    model.addAttribute("users", users);
    model.addAttribute("sortField", sortField);
    model.addAttribute("sortDir", sortDir);
    model.addAttribute("reverseSortDir", reverseSortDir);
    model.addAttribute("keyword", keyword);

    return "users/users";
  }

  @GetMapping("/users/new")
  public String newUser(Model model) {

    User user = new User();
    user.setEnabled(true);

    List<Role> listRoles = roleService.getAll();

    model.addAttribute("user", user);
    model.addAttribute("listRoles", listRoles);
    model.addAttribute("pageTitle", CREATE_NEW_USER);
    return "users/user_form";
  }


  @GetMapping("/users/edit/{id}")
  public String editUser(
        @PathVariable(name = "id") Integer id,
        RedirectAttributes redirectAttributes,
        Model model
  ) {
    log.info("(editUser) id:{}", id);
    try {
      User user = userService.get(id);
      List<Role> listRoles = roleService.getAll();

      model.addAttribute("user", user);
      model.addAttribute("listRoles", listRoles);
      model.addAttribute("role", user.getRole());


      model.addAttribute("pageTitle", EDIT_USER + id);
      return "users/user_form";
    } catch (UserNotFoundException e) {
      redirectAttributes.addFlashAttribute(MESSAGE, e.getMessage());
      return USERS_URL;
    }
  }

  @GetMapping("/users/delete/{id}")
  public String delete(
        @PathVariable Integer id,
        Model model
  ) {
    userService.delete(id);

    model.addAttribute("message", "Delete user successfully");

    return USERS_URL;
  }

  @PostMapping("/users/save")
  public String create(User user, RedirectAttributes redirectAttributes,
                       @RequestParam("image") MultipartFile multipartFile) throws IOException {
    log.info("(create) user:{}", user);

    if (!multipartFile.isEmpty()) {
      String fileName = StringUtils.cleanPath(Objects.requireNonNull(multipartFile.getOriginalFilename()));
      user.setPhotos(fileName);

      User savedUser = userService.create(user);

      String uploadDir = "user-photos/" + savedUser.getId();

      FileUploadUtil.cleanDir(uploadDir);

      FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);
    } else {
      if (user.getPhotos().isEmpty()) user.setPhotos(null);
      userService.create(user);
    }

    redirectAttributes.addFlashAttribute(MESSAGE, CREATE_USER_SUCCESS);
    log.info("multipartFile: {}", multipartFile.getOriginalFilename());

    return "redirect:/users";
  }

  @GetMapping("/reports")
  public String dashboard() {
    return "dashboard";
  }
}
