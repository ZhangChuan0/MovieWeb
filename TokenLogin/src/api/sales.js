import request from "@/utils/request.js";

//发往后端 普通支付
export function postSales(data) {
    return request({
        url: '/sales/shopping',
        method: 'POST',
        data: data
    })
}

// 验证会员信息，得到余额
export const validateUser = async (user) => {
        return request({
          url:"/sales/validateUser",
            method:"POST",
            data:user
        })
}

// 会员支付
export const postSalesByUser = async (shopping,user) => {
    const data = {
        shopping:shopping,
        user:user
    }
    return request({
        url: '/sales/shoppingByUser',
        method: 'POST',
        data:data
    })
}

// 得到订单的全部信息
export const getOrderAllInfo = ()=>{
    return request({
        method:"GET",
        url:"/sales/getOrderAllInfo",

    })
}

//更新票房
export const updateBoxOffice = ()=>{
    return request({
        method:"GET",
        url:"/sales/updateBoxOffice",
    })
}