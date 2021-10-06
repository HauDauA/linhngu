package fa.edu.controller;

import fa.edu.model.Vaccine;
import fa.edu.service.VaccineService;
import fa.edu.service.implement.VaccineServiceImpl;
import fa.edu.utils.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/admin/vaccine")
public class VaccineController {

    @Autowired
    private VaccineService vaccineService;

    @RequestMapping("/list")
    public String showList(Model model) {
        String keyword = null;
        return listByPage(model, 1, keyword);
    }

    @GetMapping("/page/{pageNumber}")
    public String listByPage(Model model, @PathVariable("pageNumber") int currentPage,
                             @Param("keyword") String keyword) {
        Page<Vaccine> page = vaccineService.getPages(currentPage, keyword);
        int totalItems = (int) page.getTotalElements();
        int totalPages = page.getTotalPages();
        long startCount = (currentPage - 1) * Constant.NUMBER_PER_PAGE + 1;
        long endCount = startCount + Constant.NUMBER_PER_PAGE - 1;
        if (endCount > page.getTotalElements()) {
            endCount = page.getTotalElements();
        }
        List<Vaccine> listVaccines = page.getContent();
        model.addAttribute("totalItems", totalItems);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("startCount", startCount);
        model.addAttribute("endCount", endCount);
        model.addAttribute("listVaccines", listVaccines);
        model.addAttribute("currentPage", currentPage);
        model.addAttribute("keyword", keyword);
        return "list-vaccine";
    }

    @GetMapping("/create")
    public String create(Model model) {
        model.addAttribute("vaccine", new Vaccine());
        return "create-vaccine";
    }

    @PostMapping("/save")
    public String save(@Valid Vaccine vaccine, BindingResult result, HttpServletRequest request,
                       RedirectAttributes redirect, Model model) {
        if (vaccineService.findByVaccineName(vaccine.getVaccineName())) {
            result.addError(new FieldError("vaccine", "vaccineName", "Vaccine already is used!"));
        }
        Date dateBegin = vaccine.getTimeBegin();
        Date dateEnd = vaccine.getTimeEnd();
        Date date = new Date();
        if (dateBegin != null) {
            if (dateBegin.before(date) ) {
                result.addError(new FieldError( "vaccine", "timeBegin", " Vaccine time begin must  today or after"));
            }
            if (dateBegin.getYear() - date.getYear() > 120) {
				result.addError(new FieldError("vaccine", "timeBegin", " Vaccine time error "));
			}
        }
        
        if (dateEnd != null) {
            if (dateEnd.before(dateBegin)) {
                result.addError(new FieldError("vaccine", "timeEnd", "Vaccine time end must  before time Begin "));
            }
            if (dateEnd.getYear() - date.getYear() > 120) {
				result.addError(new FieldError("vaccine", "timeEnd", " Vaccine time error "));
			}
        }
        if (result.hasErrors()) {
            return "create-vaccine";
        }
        redirect.addFlashAttribute("message", "Success! your registration is complete");
        vaccine.setStatus(1);
        vaccineService.save(vaccine);
        return "redirect:/admin/vaccine/list";
    }

    @GetMapping("/update/{id}")
    public String update(@PathVariable(name = "id") String id, Model model) {
        Vaccine vaccine = vaccineService.findByVaccineId(id);
        model.addAttribute("vaccine", vaccine);
        return "update-vaccine";
    }

    @PostMapping("/saveUpdate")
    public String saveUpdate(@Valid Vaccine vaccine, BindingResult result, HttpServletRequest request,
                             RedirectAttributes redirect, Model model) {
        Date dateEnd = vaccine.getTimeEnd();
        if (dateEnd != null) {
            if (dateEnd.before(dateEnd)) {
                result.addError(new FieldError("vaccine", "timeEnd", "Vaccine_time must end before time Begin "));
            }
        }
        if (result.hasErrors()) {
            return "create-vaccine";
        }
        redirect.addFlashAttribute("message", " Update vaccine information success!");
        vaccine.setStatus(1);
        vaccineService.save(vaccine);
        return "redirect:/admin/vaccine/list";
    }

    @PostMapping(value = "/delete")
    public String deleteCuss(@RequestParam(name = "idChecked", required = false) List<String> ids, Model model,
                             RedirectAttributes redirectAttributes) {
        if (ids == null) {
            redirectAttributes.addFlashAttribute("error", "No selected to delete");
        } else {
            for (String idStr : ids) {
                vaccineService.deleteById(idStr);
            }
            redirectAttributes.addFlashAttribute("success", "Delete Success");
        }
        return "redirect:/admin/vaccine/list";

    }


}
