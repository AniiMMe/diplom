package com.example.diplom.controller;

import com.example.diplom.dto.*;
import com.example.diplom.entity.Client;
import com.example.diplom.entity.InfoForIvent;
import com.example.diplom.entity.StatusOrder;
import com.example.diplom.service.*;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Controller
@AllArgsConstructor
public class UserController {

    private final UserService userService;
    private final ClientService clientService;
    private final ProvidersService providersService;
    private final AssortmentService assortmentService;
    private final OrderService orderService;
    private final ProductService productService;
    private final SupplyService supplyService;
    private final ReturnProductService returnProductService;
    private final InfoForIventService infoForIventService;

    @GetMapping("/user")
    public String getStart(Model model,Authentication authentication) {
        model.addAttribute("role", userService.getRole(authentication.getName()));
        return "/user/userpanel";
    }
    @GetMapping("/user/clients")
    public  String getClientsPage(Model model,Authentication authentication){
        model.addAttribute("clients", clientService.getAllClients());
        model.addAttribute("role", userService.getRole(authentication.getName()));
        model.addAttribute("changeClients", new ClientDTO());
        return "/admin/admin-clients";
    }
    @GetMapping("/user/newClient")
    public  String getNewClientPage(Model model,Authentication authentication){
        model.addAttribute("client", new Client());
        model.addAttribute("role", userService.getRole(authentication.getName()));
        return "/newClient";
    }
    @GetMapping("/user/providers")
    public  String getProvidersPage(Model model,Authentication authentication){
        model.addAttribute("providers", providersService.getAllProvider());
        model.addAttribute("role", userService.getRole(authentication.getName()));
        model.addAttribute("newProvider", new ProvidersDTO());
        return "/admin/admin-providers";
    }
    @GetMapping("/user/assortment")
    public String getAssortPage(Model model,Authentication authentication){
        model.addAttribute("assortments", assortmentService.getAllAssortment());
        model.addAttribute("role", userService.getRole(authentication.getName()));
        return "/admin/admin-ass";
    }
    @GetMapping("/user/products")
    public  String getProductsPage(Model model,Authentication authentication){
        model.addAttribute("products", productService.getAllProducts());
        model.addAttribute("role", userService.getRole(authentication.getName()));
        return "/admin/admin-products";
    }
    @GetMapping("/user/orders")
    public  String getOrdersPage(Model model,Authentication authentication){
        model.addAttribute("order", orderService.getAllOrder());
        model.addAttribute("role", userService.getRole(authentication.getName()));
        return "/admin/admin-order";
    }
    @GetMapping("/user/newOrder")
    public  String getNewOrderPage(Model model,Authentication authentication){
        List<OrderProductDTO> products = new ArrayList<>();
        List<String> assortment = new ArrayList<>();
        model.addAttribute("orders", new OrderDTO());
        model.addAttribute("clients", clientService.getAllClients());
        model.addAttribute("products", products);
        model.addAttribute("status", StatusOrder.getStatus());
        model.addAttribute("role", userService.getRole(authentication.getName()));
        return "/newOrder";
    }
    @GetMapping("/user/supplies")
    public  String getSuppliesPage(Model model,Authentication authentication){
        model.addAttribute("supplies", supplyService.getAllSupplies());
        model.addAttribute("role", userService.getRole(authentication.getName()));
        return "/admin/admin-supplies";
    }
    @GetMapping("/user/newSupply")
    public  String getNewSypplyPage(Model model,Authentication authentication){
        model.addAttribute("provider", providersService.getAllProvider());
        model.addAttribute("supply", new SupplyDTO());
        model.addAttribute("role", userService.getRole(authentication.getName()));
        return "/newSupply";
    }
    @GetMapping("/user/invent")
    public  String getInventPage(Model model,Authentication authentication){
        model.addAttribute("role", userService.getRole(authentication.getName()));
        model.addAttribute("iventList", infoForIventService.getAll());
        return "/admin/admin-invent";
    }
    @GetMapping("/user/newInvent")
    public  String getNewInventPage(Model model,Authentication authentication){
        model.addAttribute("assortmentList", assortmentService.getAllAssortmentForInvent());
        model.addAttribute("newIvent", new InfoForIventDTO());
        model.addAttribute("role", userService.getRole(authentication.getName()));
        return "/newInvent";
    }
    @PostMapping("/user/addNewIvent")
    public String getInfoForIvent(@ModelAttribute InfoForIventDTO infoForIventDTO,
                                  Model model,Authentication authentication, HttpSession session){
        List<InfoDTO> infoDTOS = (List<InfoDTO>) session.getAttribute("listForIvent");
        infoForIventDTO.setInfoDTOS(infoDTOS);
        model.addAttribute("infoForIvent", assortmentService.addInfoForIvent(infoForIventDTO));
        model.addAttribute("role", userService.getRole(authentication.getName()));
        return "/admin/inventSecondPage";
    }
    @GetMapping("/user/inventSecondPage")
    public String getInventSecondPage(Model model,Authentication authentication) {
        model.addAttribute("role", userService.getRole(authentication.getName()));
        return "/admin/inventSecondPage";
    }

    @GetMapping("/user/returns")
    public String getReturns(Model model,Authentication authentication) {
        model.addAttribute("returnProductList", returnProductService.getAll());
        model.addAttribute("role", userService.getRole(authentication.getName()));
        return "/admin/admin-returns";
    }

    @GetMapping("/user/newReturn")
    public String getNewReturn(Model model,Authentication authentication) {
        model.addAttribute("role", userService.getRole(authentication.getName()));
        return "/newReturn";
    }

}