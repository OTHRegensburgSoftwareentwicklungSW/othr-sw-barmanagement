package de.othr.sw.benjamineder.barmanagement.application.web.bar;

import de.othr.sw.benjamineder.barmanagement.application.coupon.service.CouponService;
import de.othr.sw.benjamineder.barmanagement.application.web.auth.BarUserAccess;
import de.othr.sw.benjamineder.barmanagement.application.web.model.CouponModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/coupon/redeem")
public class RedeemCouponController {

  private final CouponService couponService;

  @Autowired
  public RedeemCouponController(CouponService couponService) {
    this.couponService = couponService;
  }

  @BarUserAccess
  @GetMapping
  public String redeemCouponForm(Model model) {
    model.addAttribute("couponModel", new CouponModel())
         .addAttribute("response", false);
    return "bar/bar_redeem-coupon";
  }

  @BarUserAccess
  @PostMapping
  public String redeemCoupon(@ModelAttribute CouponModel coupon, Model model) {
    try {
      var couponId = coupon.getId();
      model.addAttribute("coupon", couponService.redeemCoupon(couponId));
      model.addAttribute("redeemed", true);
    } catch (IllegalArgumentException | IllegalStateException e) {
      model.addAttribute("redeemed", false);
      model.addAttribute("errorMessage", e.getMessage());
    }
    model.addAttribute("couponModel", new CouponModel())
         .addAttribute("response", true);
    return "bar/bar_redeem-coupon";
  }
}
