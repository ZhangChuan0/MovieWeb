import request from '@/utils/request'

//查询单个会员信息
export const getMemberInfo = (data) => {
    return request({
        url: '/members/isSave',
        method: 'post',
        data
    })
}

//保存或更新

export const saveOrUpdate = (data) => {
    return request({
        url: '/members/saveOrUpdate',
        method: 'post',
        data
    })
}


//    获取指定的member用户
export const getOneMember = (data)=>{
    return request({
        method:"POST",
        url:"/members/getMemberByIdAndName",
        data
    })

}

//充值
export const recharge = (data)=>{
    return request({
        method:"POST",
        url:"/members/recharge",
        data
    })
}

//更改密码
export const changeEditPassword = (data)=>{
    return request({
        method:"POST",
        url:"/members/changePassword",
        data: data
    })
}
export const resetPassword = (data)=>{
    return request({
        method:"POST",
        url:"/members/resetPassword",
        data: data
    })
}

// 会员的历史消费
export const getMemberHistoryConsume = (data)=>{
    return request({
        method:"POST",
        url:"/members/getMemberHistoryConsume",
        data: data
    })

}

// 退票
export const returnTicket = (id)=>{
    return request({
        method:"GET",
        url:"/members/returnTicket",
        params: {id}

    })
}



