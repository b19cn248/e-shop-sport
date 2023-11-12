package com.shopsport.server.service.impl;

import com.shopsport.common.entity.Brand;
import com.shopsport.server.exception.brand.BrandNotFoundException;
import com.shopsport.server.repository.BrandRepository;
import com.shopsport.server.service.BrandService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;

@RequiredArgsConstructor
@Slf4j
public class BrandServiceImpl implements BrandService {

  public static final int BRANDS_PER_PAGE = 10;

  private final BrandRepository repository;

  @Override
  public List<Brand> list() {
    return repository.findAll();
  }

  @Override
  public List<Brand> listAll() {
    log.info("(List all by id and name)");
    return repository.listAll();
  }

  @Override
  public Page<Brand> listByPage(int page, String sortField, String sortDir, String keyword) {
    Sort sort = Sort.by(sortField);

    sort = sortDir.equals("asc") ? sort.ascending() : sort.descending();

    Pageable pageable = PageRequest.of(page - 1, BRANDS_PER_PAGE, sort);

    if (Objects.nonNull(keyword)) {
      return repository.findAll(keyword, pageable);
    }

    return repository.findAll(pageable);
  }

  @Override
  public Brand save(Brand brand) {
    return repository.save(brand);
  }

  @Override
  public Brand get(Integer id) {
    log.info("(get) id:{}", id);
    try {
      return repository.findById(id).orElseThrow();
    } catch (NoSuchElementException ex) {
      throw new BrandNotFoundException("Could not find any brand with ID " + id);
    }
  }

  @Override
  public void delete(Integer id) {
    Long countById = repository.countById(id);

    if (countById == null || countById == 0) {
      throw new BrandNotFoundException("Could not find any brand with ID " + id);
    }

    repository.deleteById(id);
  }

  @Override
  public String checkUnique(Integer id, String name) {
    boolean isCreatingNew = (id == null || id == 0);
    Brand brandByName = repository.findByName(name);

    if (isCreatingNew) {
      if (brandByName != null) return "Duplicate";
    } else {
      if (brandByName != null && !Objects.equals(brandByName.getId(), id)) {
        return "Duplicate";
      }
    }

    return "OK";
  }
}
