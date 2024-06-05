import request from "@/utils/request.js";
// 接口
// 课程列表

export const getQueryCourses = (params) => {
        return request({
            method:"POST",
            url:"/boss/courses/getQueryCourses",
            data: params
        })
}
// 课程上下架
export const changeState = (courseId,status) => {
    return request({
        method:"GET",
        url:"/boss/courses/changeState",
        params: {
            courseId,
            status
        }
    })
}

//保存或更新课程信息

export const saveUpdateCourse=(course)=>{
    return request({
        method:"post",
        url:"/boss/courses/saveUpdateCourse",
        data:course
    })

}
// large——course的info
export const getCourseById01 = (courseId)=>{
    return request({
        method: "get",
        url: "/boss/courses/getCourseById",
        params: {
            courseId,
        }
    })

}




//获取指定id的课程信息
export const getCourseById=(courseId)=>{
    return request({
        method: "get",
        url: "/boss/courses/getCourseNameById",
        params: {
            courseId,
        }
    })
}

// 获得指定章节课时
export const getSectionAndLesson=(courseId)=>{
    return  request({
        method:"GET",
        url:"/boss/courses/selection/getSectionAndLesson",
        params:{
            courseId
        }

    })

}


//根据id获取章节信息
export const getSectionById = (code,name)=>{
    return request({
        method:"GET",
        url:"/boss/courses/selection/getSectionById",
        params:{
            code,name
        }

    })


}


// 增加courseTheme的一个实例
export const saveCourseTheme=(courseTheme)=>{
    return request({
        method:"POST",
        url:"/boss/courses/saveCourseTheme",
        data:courseTheme

    })

}

//     删除一条
export const deleteById = (courseId)=>{
    return request({
        method:"DELETE",
        url:"/boss/courses/deleteById",
        params:{
            courseId
        }
    })
}
// 查找一条根据id
export const getCourse_largeById = (courseId)=>{
    return request({
        method:"GET",
        url:"/boss/courses/getCourse_largeById",
        params:{courseId}
    })
}
