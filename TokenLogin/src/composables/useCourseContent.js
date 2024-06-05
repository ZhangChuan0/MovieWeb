import {getCourseById,getSectionAndLesson} from "@/api/courses.js";


//保存结果
export const course =ref({
    name:"x",
    id:''
})

//保存课程大纲
export const courseSAL = ref({})


//获取方法
export const loadSAL = async (courseId) => {
    const {data} = await getCourseById(courseId)
    // console.log(data.data.records)
    if (data.code === "000000"){
        course.value  = data.data.records[0]
    }

    const {data:dataSAL} = await getSectionAndLesson(courseId)
    console.log("SAl:",dataSAL.records)
        if (dataSAL.code === "000000"){
        courseSAL.value = dataSAL.records

        }
}


//