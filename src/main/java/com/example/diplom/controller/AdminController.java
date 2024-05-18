package com.example.diplom.controller;

import com.example.diplom.dto.InfoDTO;
import com.example.diplom.dto.InfoForIventDTO;
import com.example.diplom.dto.OrderDTO;
import com.example.diplom.dto.OrderProductDTO;
import com.example.diplom.entity.*;
import com.example.diplom.service.*;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Controller
@AllArgsConstructor
public class AdminController {
    private final UserService userService;
    private final ClientService clientService;
    private final ProvidersService providersService;
    private final AssortmentService assortmentService;
    private final OrderService orderService;
    private final ProductService productService;
    private final SupplyService supplyService;
    private final ReturnProductService returnProductService;

    @GetMapping("/admin")
    public String getStart() {
        return "/admin/adminpanel";
    }

    @GetMapping("/admin/users")
    public String getUsersPage(Model model) {
        model.addAttribute("workers", userService.getAllWorkers());
        return "/admin/users";
    }
    @GetMapping("/admin/clients")
    public  String getClientsPage(Model model){
        model.addAttribute("clients", clientService.getAllClients());
        return "/admin/admin-clients";
    }
    @GetMapping("/admin/providers")
    public  String getProvidersPage(Model model){
        model.addAttribute("providers", providersService.getAllProvider());
        return "/admin/admin-providers";
    }
    @GetMapping("/admin/assortment")
    public String getAssortPage(Model model){
        model.addAttribute("assortments", assortmentService.getAllAssortment());
        return "/admin/admin-ass";
    }
    @GetMapping("/admin/products")
    public  String getProductsPage(Model model){
        model.addAttribute("products", productService.getAllProducts());
        return "/admin/admin-products";
    }
    @GetMapping("/admin/orders")
    public  String getOrdersPage(Model model){
        model.addAttribute("order", orderService.getAllOrder());
        return "/admin/admin-order";
    }
    @GetMapping("/admin/invent")
    public  String getInventPage(){
        return "/admin/admin-invent";
    }
    @GetMapping("/admin/supplies")
    public  String getSuppliesPage(Model model){
        model.addAttribute("supplies", supplyService.getAllSupplies());
        return "/admin/admin-supplies";
    }
    @GetMapping("/admin/newClient")
    public  String getNewClientPage(Model model){
        model.addAttribute("client", new Client());
        return "/newClient";
    }
    @GetMapping("/admin/newProvider")
    public  String getNewProviderPage(Model model){
        model.addAttribute("provider", new Providers());
        return "/newProvider";
    }
    @GetMapping("/admin/newAssortment")
    public  String getNewAssortmentrPage(Model model){
        model.addAttribute("assortment", new Assortment());
        model.addAttribute("formV", FormVipuska.getRussianName());
        return "/newAss";
    }
    @GetMapping("/admin/newUser")
    public  String getNewUserPage(Model model){

        model.addAttribute("user", new Workers());
        return "/newUser";
    }
    @GetMapping("/admin/newSupply")
    public  String getNewSypplyPage(Model model){
        model.addAttribute("provider", providersService.getAllProvider());
        model.addAttribute("supply", new SupplyDTO());
        return "/newSupply";
    }
    @GetMapping("/admin/newOrder")
    public  String getNewOrderPage(Model model){
        List<OrderProductDTO> products = new ArrayList<>();
        List<String> assortment = new ArrayList<>();
        model.addAttribute("orders", new OrderDTO());
        model.addAttribute("clients", clientService.getAllClients());
        model.addAttribute("products", products);
        model.addAttribute("status", StatusOrder.getStatus());
        return "/newOrder";
    }
    @GetMapping("/admin/newInvent")
    public  String getNewInventPage(Model model){
        model.addAttribute("assortmentList", assortmentService.getAllAssortmentForInvent());
        model.addAttribute("newIvent", new InfoForIventDTO());
        return "/newInvent";
    }

    @PostMapping("/admin/addNewIvent")
    public String getInfoForIvent(@ModelAttribute InfoForIventDTO infoForIventDTO,
                                  Model model, HttpSession session){
        List<InfoDTO> infoDTOS = (List<InfoDTO>) session.getAttribute("listForIvent");
        infoForIventDTO.setInfoDTOS(infoDTOS);
        model.addAttribute("infoForIvent", assortmentService.addInfoForIvent(infoForIventDTO));
        return "/admin/inventSecondPage";
    }
    @GetMapping("/admin/inventSecondPage")
    public String getInventSecondPage() {
        return "/admin/inventSecondPage";
    }

    @GetMapping("/admin/returns")
    public String getReturns(Model model) {
        model.addAttribute("returnProductList", returnProductService.getAll());
        return "/admin/admin-returns";
    }

    @GetMapping("/admin/newReturn")
    public String getNewReturn() {
        return "/newReturn";
    }
    @GetMapping("/admin/analit")
    public String getAnailt() {
        return "/admin/admin-analit";
    }
    @GetMapping("/admin/otch")
    public String getOtch() {
        return "/admin/admin-otch";
    }
    @GetMapping("/admin/otch2")
    public String getOtch2() {
        return "/admin/admin-otch2";
    }

}