package com.inhatc.cs;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.inhatc.domain.BoardVO;
import com.inhatc.domain.Criteria;
import com.inhatc.domain.PageMaker;
import com.inhatc.service.BoardService;

import javax.inject.Inject;

@Controller
@RequestMapping("/board/*")
public class BoardController {
    @Inject
    private BoardService service;

    @GetMapping("/listAll")
    public String listAll(Model model) throws Exception {

        System.out.println("listAll(GET)");
        model.addAttribute("list", service.listAll());
        return "/board/listAll";
    }

    @GetMapping("/read")
    public String read(@RequestParam("bno") int bno, Model model) throws Exception {

        model.addAttribute(service.read(bno));
        return "/board/read";
    }


    @GetMapping("/register")
    public String registerGET(BoardVO board, Model model) throws Exception {

        System.out.println("register(GET)");
        return "/board/register";
    }

    @PostMapping("/register")
    public String registerPOST(BoardVO board, Model model) throws Exception {

        System.out.println("register(POST)");

        service.regist(board);
        model.addAttribute("result", "success");

        return "/board/success";
    }



    @PostMapping("/remove")
    public String remove(@RequestParam("bno") int bno, RedirectAttributes rttr) throws Exception {

        service.remove(bno);

        rttr.addFlashAttribute("msg", "SUCCESS");

        return "redirect:/board/listAll";
    }

    @GetMapping("/modify")
    public String modifyGET(int bno, Model model) throws Exception {

        model.addAttribute(service.read(bno));
        return "/board/modify";
    }

    @RequestMapping(value = "/modify", method = RequestMethod.POST)
    public String modifyPOST(BoardVO board, RedirectAttributes rttr) throws Exception {

        service.modify(board);
        rttr.addFlashAttribute("msg", "SUCCESS");

        return "redirect:/board/listAll";
    }

    @GetMapping("/listCri")
    public String listAll(Criteria cri, Model model) throws Exception {

        System.out.println("show list Page with Criteria......................");

        model.addAttribute("list", service.listCriteria(cri));
        return "/board/listCri";
    }

    @GetMapping("/listPage")
    public String listPage(@ModelAttribute("cri") Criteria cri, Model model) throws Exception {

        System.out.println(cri.toString());

        model.addAttribute("list", service.listCriteria(cri));
        PageMaker pageMaker = new PageMaker();
        pageMaker.setCri(cri);
        // pageMaker.setTotalCount(131);

        pageMaker.setTotalCount(service.listCountCriteria(cri));

        model.addAttribute("pageMaker", pageMaker);
        return "/board/listPage";
    }

    @GetMapping("/readPage")
    public void read(@RequestParam("bno") int bno, @ModelAttribute("cri") Criteria cri, Model model) throws Exception {

        model.addAttribute(service.read(bno));
    }

    @PostMapping("/removePage")
    public String remove(@RequestParam("bno") int bno, Criteria cri, RedirectAttributes rttr) throws Exception {

        service.remove(bno);
        rttr.addAttribute("page", cri.getPage());
        rttr.addAttribute("perPageNum", cri.getPerPageNum());
        rttr.addFlashAttribute("msg", "SUCCESS");

        return "redirect:/board/listPage";
    }

    @GetMapping("/modifyPage")
    public void modifyPagingGET(@RequestParam("bno") int bno, @ModelAttribute("cri") Criteria cri, Model model)
            throws Exception {

        model.addAttribute(service.read(bno));
    }

    @PostMapping("/modifyPage")
    public String modifyPagePOST(BoardVO board, Criteria cri, RedirectAttributes rttr) throws Exception {

        System.out.println("mod post............");

        service.modify(board);
        rttr.addAttribute("page", cri.getPage());
        rttr.addAttribute("perPageNum", cri.getPerPageNum());
        rttr.addFlashAttribute("msg", "SUCCESS");

        return "redirect:/board/listPage";
    }

}
