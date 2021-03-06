package com.kodilla.ecommercee.domain;

import com.kodilla.ecommercee.controller.GroupController;
import com.kodilla.ecommercee.mapper.GroupMapper;
import com.kodilla.ecommercee.repository.GroupRepository;
import com.kodilla.ecommercee.repository.ProductRepository;
import com.kodilla.ecommercee.service.GroupService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class GroupEntityTestSuite {
    @Autowired
    GroupRepository groupRepo;
    @Autowired
    GroupController groupController;
    @Autowired
    GroupMapper groupMapper;
    @Autowired
    ProductRepository productRepo;
    @Autowired
    GroupService groupService;

    @Test
    public void shouldSaveAndReadSingleGroupTest() {
        //Given
        groupRepo.deleteAll();
        GroupEntity testGroup = new GroupEntity("test name");
        //When&Then
        groupRepo.save(testGroup);
        assertTrue(groupRepo.existsById(testGroup.getId()));
        Optional<GroupEntity> receivedGroup = groupRepo.findById(testGroup.getId());
        assertTrue(receivedGroup.isPresent());
        receivedGroup.ifPresent(group -> assertEquals("test name", group.getName()));
    }

    @Test
    public void shouldUpdateExistingGroupTest() {
        //Given
        groupRepo.deleteAll();
        productRepo.deleteAll();
        GroupEntity testGroup = new GroupEntity("test name");
        ProductEntity testProduct1 = new ProductEntity("test name", "description", 1.0, null);
        ProductEntity testProduct2 = new ProductEntity("test name", "description", 1.0, null);
        groupRepo.save(testGroup);
        Long id = testGroup.getId();
        String secondName = "new name";
        //When
        testProduct1.setGroup(testGroup);
        testProduct2.setGroup(testGroup);
        productRepo.save(testProduct1);
        productRepo.save(testProduct2);
        testGroup.setProducts(new ArrayList<>(Arrays.asList(testProduct1, testProduct2)));
        testGroup.setName(secondName);
        groupRepo.save(testGroup);
        //Then
        assertEquals(2, testGroup.getProducts().size());
        assertEquals(secondName, testGroup.getName());
    }

    @Test
    public void shouldRemoveSingleGroup() {
        //Given
        groupRepo.deleteAll();
        productRepo.deleteAll();
        GroupEntity testGroup = new GroupEntity("test name");
        ProductEntity testProduct1 = new ProductEntity("test name", "description", 1.0, testGroup);
        ProductEntity testProduct2 = new ProductEntity("test name", "description", 1.0, testGroup);
        groupRepo.save(testGroup);
        productRepo.save(testProduct1);
        productRepo.save(testProduct2);
        //When
        groupRepo.deleteById(testGroup.getId());
        //Then
        assertFalse(groupRepo.existsById(testGroup.getId()));
        assertFalse(productRepo.existsById(testProduct1.getId()));
        assertFalse(productRepo.existsById(testProduct2.getId()));
    }
}
