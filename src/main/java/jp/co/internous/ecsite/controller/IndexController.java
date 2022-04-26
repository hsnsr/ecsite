package jp.co.internous.ecsite.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;

import jp.co.internous.ecsite.model.dao.GoodsRepository;
import jp.co.internous.ecsite.model.dao.PurchaseRepository;
import jp.co.internous.ecsite.model.dao.UserRepository;
import jp.co.internous.ecsite.model.dto.HistoryDto;
import jp.co.internous.ecsite.model.dto.LoginDto;
import jp.co.internous.ecsite.model.entity.Goods;
import jp.co.internous.ecsite.model.entity.Purchase;
import jp.co.internous.ecsite.model.entity.User;
import jp.co.internous.ecsite.model.form.CartForm;
import jp.co.internous.ecsite.model.form.HistoryForm;
import jp.co.internous.ecsite.model.form.LoginForm;

@Controller
@RequestMapping("/ecsite")
public class IndexController {

	@Autowired
	private GoodsRepository goodsRepos;
	//GoodsエンティティからgoodsテーブルにアクセスするためのDAO

	@Autowired
	private UserRepository userRepos;
	//UserエンティティからuserテーブルにアクセスするためのDAO
	
	@Autowired
	private PurchaseRepository purchaseRepos;
	//PurchaseエンティティからpurchaseテーブルにアクセスするためのDAO
	
	private Gson gson = new Gson();
	//WebサービスAPIとして作成するためJSON形式を扱えるようGsonをインスタンス化しておく
	
	@RequestMapping("/")
	public String index(Model m) {
		List<Goods> goods = goodsRepos.findAll();
		m.addAttribute("goods",goods);
		//goodsテーブルから取得した商品エンティティの一覧をフロントに渡すModelに追加。
		
		return "index";
	}
	
	@ResponseBody
	@PostMapping("/api/login")
	public String loginApi(@RequestBody LoginForm form) {
		List<User> users = userRepos.findByUserNameAndPassword(form.getUserName(), form.getPassword());
		//userテーブルから検索結果を取得
		
		LoginDto dto = new LoginDto(0, null, null, "ゲスト");
		if(users.size()>0) {
			dto = new LoginDto(users.get(0));
		}
		//DTOをゲストの情報で初期化し検索結果が存在していた場合のみ、実際のユーザー情報をDTOに詰める
		
		return gson.toJson(dto);
		//最終的にDTOをJSONオブジェクトとして画面側に返す	
	}
	
	@ResponseBody
	@PostMapping("/api/purchase")
	public String purchaseApi(@RequestBody CartForm f) {
		
		f.getCartList().forEach((c) -> {
			long total = c.getPrice() * c.getCount();
			purchaseRepos.persist(f.getUserId(), c.getId(), c.getGoodsName(), c.getCount(), total);
		});
		return String.valueOf(f.getCartList().size());	
	}//購入処理を担うメソッド
	
	@ResponseBody
	@PostMapping("/api/history")
	public String historyApi(@RequestBody HistoryForm form) {
		String userId = form.getUserId();
		List<Purchase> history = purchaseRepos.findHistory(Long.parseLong(userId));
		List<HistoryDto> historyDtoList = new ArrayList<>();
		history.forEach((v) -> {
			HistoryDto dto = new HistoryDto(v);
			historyDtoList.add(dto);
		});
		return gson.toJson(historyDtoList);
	}//購入履歴を表示するメソッド
}
