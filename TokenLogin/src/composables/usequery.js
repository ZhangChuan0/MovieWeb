import {queryCourses,queryResult} from "@/composables/useCourses.js";
import {getLanguage, getType01, getYear01} from "@/api/query.js";
import {ElMessage} from "element-plus";


export const getMovieList = async (par={})=>{
    if (par){
        await queryCourses(par);
    }else {
        await queryCourses()
    }

    movieList.value = queryResult.value.records
    if (queryResult.value.records.length !== 0){
        ElMessage.success('已找到'+queryResult.value.records.length+'部相关影片')
    }else {
        ElMessage.error('未找到相关影片')
    }
}


export const movieList = ref([])


//类型
export const typeList = ref([])

export const getType = async () => {
    await queryCourses();

    // 从 queryResult 中获取记录
    const records = queryResult.value.records;

    // 定义用于存储筛选后的类型的 Set
    const typeSet = new Set();

    // 遍历记录并将 previewFirstField 和 previewSecondField 添加到 Set 中
    records.forEach(record => {
        if (record.previewFirstField) {
            typeSet.add(record.previewFirstField.trim());
        }
        if (record.previewSecondField) {
            typeSet.add(record.previewSecondField.trim());
        }
    });

    // 将 Set 转换为数组，并将数组赋值给 typeList
    typeList.value = Array.from(typeSet);
    console.log("after",typeSet)
};


// 得到年份
export const salesList = ref([])
export const getYear = async () => {
    await queryCourses();

    // 从 queryResult 中获取记录
    const records = queryResult.value.records;

    // 定义用于存储筛选后的销售数据的 Set
    const salesSet = new Set();

    // 遍历记录并将 sales 字段添加到 Set 中
    records.forEach(record => {
        salesSet.add(record.sales);
    });

    // 将 Set 转换为数组，并将数组赋值给 salesList，去除重复值

    salesList.value = Array.from(salesSet);
    //有序排序
    salesList.value.sort((a, b) => a - b);
};


// 处理类型的方法
export const handleType = async (type) => {
    const {data} = await getType01(type)
    if (data.data.length !== 0){
        ElMessage.success('已找到'+data.data.length+'部相关影片')
    }else {
        ElMessage.error('未找到相关影片')
    }
   movieList.value = data.data
}
//处理日期
export const handleDate = async (date) => {
    const {data} = await getYear01(date)
    if (data.data.length !== 0){
        ElMessage.success('已找到'+data.data.length+'部相关影片')
    }else {
        ElMessage.error('未找到相关影片')
    }
 movieList.value = data.data
}
//处理语言
export const handleLanguage = async (language) => {
    const {data} = await getLanguage(language)

    if (data.data.length !== 0){
        ElMessage.success('已找到'+data.data.length+'部相关影片')
    }else {
        ElMessage.error('未找到相关影片')
    }
    movieList.value = data.data
}