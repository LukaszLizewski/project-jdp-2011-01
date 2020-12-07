package com.kodilla.ecommercee.mapper;

import com.kodilla.ecommercee.controller.GroupNotFoundException;
import com.kodilla.ecommercee.domain.CartEntity;
import com.kodilla.ecommercee.domain.OrderEntity;
import com.kodilla.ecommercee.domain.ProductDto;
import com.kodilla.ecommercee.domain.ProductEntity;
import com.kodilla.ecommercee.repository.GroupRepository;
import com.kodilla.ecommercee.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ProductMapper {
    @Autowired
    private GroupRepository groupRepo;

    public ProductEntity mapToProduct(final ProductDto dto) throws GroupNotFoundException {
        return new ProductEntity(
                dto.getId(),
                dto.getName(),
                dto.getDescription(),
                dto.getPrice(),
                groupRepo.findById(dto.getGroupId()).orElseThrow(GroupNotFoundException::new)
        );
    }

    public ProductDto mapToProductDto(final ProductEntity product) {
        List<Long> cartIds = product.getCarts()
                .stream()
                .map(CartEntity::getCartId)
                .collect(Collectors.toList());
        List<Long> orderIds = product.getOrders()
                .stream()
                .map(OrderEntity::getOrderId)
                .collect(Collectors.toList());
        return new ProductDto(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getPrice(),
                product.getGroup().getId(),
                cartIds,
                orderIds
        );
    }

    public List<ProductDto> mapToProductDtoList(final List<ProductEntity> productEntitiesList) {
        ProductMapper mapper = new ProductMapper();
        return productEntitiesList
                .stream()
                .map(mapper::mapToProductDto)
                .collect(Collectors.toList());
    }
}
