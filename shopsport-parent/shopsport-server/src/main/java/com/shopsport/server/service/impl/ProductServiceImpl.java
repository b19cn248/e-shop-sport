package com.shopsport.server.service.impl;

import com.shopsport.common.entity.Product;
import com.shopsport.server.dto.ProductResponse;
import com.shopsport.server.dto.RevenueStatisticsByMonth;
import com.shopsport.server.exception.product.ProductNotFoundException;
import com.shopsport.server.repository.ProductRepository;
import com.shopsport.server.repository.PromotionRepository;
import com.shopsport.server.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

@Slf4j
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

  private final ProductRepository repository;
  private final PromotionRepository promotionRepository;

  @Override
  public List<Product> list() {
    log.info("(list all Product)");
    return repository.findAll();
  }

  @Override
  public Product save(Product product) {

    log.info("(save) product:{}", product);

    String alias = product.getAlias();
    if (Objects.isNull(alias) || alias.isEmpty()) {
      String defaultAlias = product.getName().replace(" ", "-");
      product.setAlias(defaultAlias.toLowerCase());
    } else {
      product.setAlias(alias.replace(" ", "-").toLowerCase());
    }

    return repository.save(product);
  }

  @Override
  public String checkUnique(Integer id, String name) {
    log.info("(checkUnique) id:{}, name:{}", id, name);

    boolean isCreatingNew = (id == null || id == 0);
    Product productByName = repository.findByName(name);

    if (isCreatingNew) {
      if (productByName != null) return "Duplicate";
    } else {
      if (productByName != null && productByName.getId() != id) {
        return "Duplicate";
      }
    }

    return "OK";
  }

  @Override
  public Product get(Integer id) throws ProductNotFoundException {
    log.info("(get) id:{}", id);
    try {
      return repository.findById(id).get();
    } catch (NoSuchElementException ex) {
      throw new ProductNotFoundException("Could not find product with id " + id);
    }
  }

  @Override
  public List<ProductResponse> statisticsByBrand() {
    log.info("(statisticsByBrand)");
    return repository.statisticsByBrand();
  }

  @Override
  public List<RevenueStatisticsByMonth> statisticsByMonth() {
    log.info("(statisticsByMonth)");

    List<RevenueStatisticsByMonth> revenueStatisticsByMonths = new ArrayList<>();

    revenueStatisticsByMonths.add(new RevenueStatisticsByMonth(9, getTotalMoneyOfMonth(9)));
    revenueStatisticsByMonths.add(new RevenueStatisticsByMonth(10, getTotalMoneyOfMonth(10)));
    revenueStatisticsByMonths.add(new RevenueStatisticsByMonth(11, getTotalMoneyOfMonth(11)));


    return revenueStatisticsByMonths;
  }


  private Double getTotalMoneyOfMonth(Integer month) {
    int year = LocalDate.now().getYear();

    LocalDate startOfMonth = LocalDate.of(year, month, 1);
    LocalDate endOfMonth = startOfMonth.withDayOfMonth(startOfMonth.lengthOfMonth());

    Date startDate = Date.from(startOfMonth.atStartOfDay(ZoneId.systemDefault()).toInstant());
    Date endDate = Date.from(endOfMonth.atStartOfDay(ZoneId.systemDefault()).toInstant());

    Long startTimestamp = startDate.getTime();
    Long endTimestamp = endDate.getTime();

    return repository.totalMoney(startTimestamp, endTimestamp) - promotionRepository.getMoneyDeductedOfPromotion(startTimestamp, endTimestamp);
  }


}
