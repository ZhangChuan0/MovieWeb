import request from "@/utils/request.js";
// 得到year的接口
export const getYear01 = (year) => {
    return request({
        url: "/query/getYear",
        method: "GET",
        params: {year}
    })
}

//type
export const getType01 = (data) => {
    return request({
        url: "/query/getType",
        method: "POST",
        data: {data:data}
    })
}

// language
export const getLanguage = (data) => {
    return request({
        url: "/query/getLanguage",
        method: "POST",
        data: {data:data}
    })
}