package service;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.LinkedList;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.geosieben.gsbworkday.entity.ProjectCategories;
import com.geosieben.gsbworkday.repository.ProjectcategoryRepository;
import com.geosieben.gsbworkday.service.CategoryService;
@ExtendWith(MockitoExtension.class)
public class CategoryServiceTest {
@Mock
private ProjectcategoryRepository projectcategoryRepository;
@InjectMocks
private CategoryService categoryService;
@Test
public void testGetAllCategories(){
    ArrayList<ProjectCategories> mock=new ArrayList<>();
    ProjectCategories cat1=new ProjectCategories();
    cat1.setCatagoryShortname("LIDAR");
    cat1.setCategoryName("LIDAR");
        ProjectCategories cat2=new ProjectCategories();
   cat2.setCatagoryShortname("MLS");
    cat2.setCategoryName("MLS");

mock.add(cat2);
mock.add(cat1);
    when(categoryService.getAllCategories()).thenReturn(mock);
    assertNotNull(categoryService.getAllCategories());
  assertTrue(categoryService.getAllCategories() instanceof ArrayList);
}
}
