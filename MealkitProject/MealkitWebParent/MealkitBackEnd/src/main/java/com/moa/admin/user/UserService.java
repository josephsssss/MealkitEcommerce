package com.moa.admin.user;

import java.util.List;
import java.util.NoSuchElementException;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.moa.common.entity.Role;
import com.moa.common.entity.User;

@Service
@Transactional
public class UserService {
	public static final int USER_PER_PAGE = 1;

	@Autowired
	private UserRepository userRepo;

	@Autowired
	private RoleRepository roleRepo;

	@Autowired
	private PasswordEncoder passwordEncoder;

	public List<User> listAll() {
		return (List<User>) userRepo.findAll();
	}

	public Page<User> listByPage(int pageNum, String sortField, String sortDir, String keyword) {
		Sort sort = Sort.by(sortField);
		sort = sortDir.equals("asc") ? sort.ascending() : sort.descending();
		Pageable pageable = PageRequest.of(pageNum - 1, USER_PER_PAGE, sort);
		if (keyword != null) {
			return userRepo.findAll(keyword, pageable);
		}
		return userRepo.findAll(pageable);
	}

	public List<Role> listRoles() {
		return (List<Role>) roleRepo.findAll();
	}

	public User save(User user) {
		boolean isUpdatingUser = (user.getId() != null);
		if (isUpdatingUser) {
			User existingUser = userRepo.findById(user.getId()).get();
			if (user.getPassword().isEmpty()) {
				user.setPassword(existingUser.getPassword());
			} else {
				encodePassword(user);
			}
		} else {
			encodePassword(user);
		}
		return userRepo.save(user);
	}

	private void encodePassword(User user) {
		String encodedPassword = passwordEncoder.encode(user.getPassword());
		user.setPassword(encodedPassword);
	}

	public boolean isEmailUnique(Integer id, String email) {
		User userByEmail = userRepo.getUserByEmail(email);
		if (userByEmail == null)
			return true;
		boolean isCreatingNew = (id == null);
		if (isCreatingNew) {
			if (userByEmail != null)
				return false;
		} else {
			if (userByEmail.getId() != id) {
				return false;
			}
		}
		return true;
	}

	public User get(Integer id) throws UserNotFoundException {
		try {
			return userRepo.findById(id).get();
		} catch (NoSuchElementException e) {
			throw new UserNotFoundException("다음 아이디를 가진 회원을 찾지 못했습니다 ID:" + id);
		}
	}

	public void delete(Integer id) throws UserNotFoundException {
		Long countById = userRepo.countById(id);
		if (countById == null || countById == 0) {
			throw new UserNotFoundException("다음 아이디를 가진 회원을 찾지 못했습니다 ID:" + id);
		}
		userRepo.deleteById(id);
	}

	public void updateUserEnabledStatus(Integer id, boolean enabled) {
		userRepo.updateEnabledStatus(id, enabled);
	}
}
