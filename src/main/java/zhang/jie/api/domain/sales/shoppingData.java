package zhang.jie.api.domain.sales;

import zhang.jie.api.domain.member.Member;

import java.util.List;

/**
 * Created by zhouhuanping$ on 2024/5/4$.
 */
public class shoppingData {
    public List<Order> getShopping() {
        return shopping;
    }

    public void setShopping(List<Order> shopping) {
        this.shopping = shopping;
    }

    public Member getUser() {
        return user;
    }

    public void setUser(Member user) {
        this.user = user;
    }

    private List<Order> shopping;
    private Member user;

}
