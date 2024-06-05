import request from '@/utils/request'

// 座位信息 根据电影id
export const getSeat = (movieId) => {
    return request({
        url: `/sales/getSeat`,
        method: 'get',
        params: {movieId}
    })
}


//买票
export const buyTicket = (data)=>{
    return request({
        url: `/sales/buyTicket`,
        method:"POST",
        data: data
    })
}

// 会员买电影
export const buyMovie = (data,user)=>{
    return request({
        url: `/sales/buyMovie`,
        method:"POST",
        data: {
                data,user
        }
    })
}



