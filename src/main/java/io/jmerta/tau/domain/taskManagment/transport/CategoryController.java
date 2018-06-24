package io.jmerta.tau.domain.taskManagment.transport;

import io.jmerta.tau.domain.taskManagment.service.ManageCategory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/category")
public class CategoryController {

    private ManageCategory manageCategory;

    @Autowired
    public CategoryController(ManageCategory manageCategory) {
        this.manageCategory = manageCategory;
    }


}
