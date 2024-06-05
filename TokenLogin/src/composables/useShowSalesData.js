// 整合数据
import { ref,reactive } from 'vue';
import {queryResult,queryCourses} from "@/composables/useCourses.js"
import {getSeat} from "@/api/movie.js";
import {getOrderAllInfo} from "@/api/sales.js";

export const dataOfMovie = ref([])





export const getMovie = async () => {
    await queryCourses({ page: 1 });

    dataOfMovie.value = queryResult.value.records.map(record => {
        return {
            id: record.id,
            value: [record.price, record.activityStock, record.discounts],
            name: record.courseName
        };
    });

    // 获取每个电影的座位信息
    for (const movie of dataOfMovie.value) {
        const seats = await getSeat(movie.id); // 将电影的 id 作为参数传递给 getSeat 函数
        const statusMinusOneCount = seats.data.records.filter(seat => seat.status === "-1").length; // 统计 status 为 -1 的数量
        movie.value.push(statusMinusOneCount); // 将数量添加到 value 中
    }

    const {data} = await getOrderAllInfo()

    for (const movie of dataOfMovie.value) {
        const records = data.records.filter(record => record.item_name === movie.name);
        const sum = records.reduce((total, record) => total + parseInt(record.totalAmount), 0);
        movie.value.push(sum);
    }

}



export const result = reactive({
    seatCount: 0,
    boxOffice: 0,
});


export const membersCount = ref({
    member:0,
    custom:0

})

// 拿到电影的名字了
export const activeName = ref("")

export const handleName = async (name) => {
    let movie = null;
    // 在 dataOfMovie.value 中查找 name 对应的电影对象
    for (const m of dataOfMovie.value) {
        if (m.name === name) {
            movie = m;
            break;
        }
    }

    // 如果找到了对应的电影对象
    if (movie) {
        // 从 movie.value 中提取座位数量和票房数量
        const seatCount = movie.value[3]; // 假设座位数量在 value 数组的第 4 个位置
        const boxOffice = movie.value[1]; // 假设票房数量在 value 数组的第 2 个位置

        // 更新 result 对象的属性值
        result.seatCount = seatCount;
        result.boxOffice = boxOffice;
    } else {
        // 如果没有找到对应的电影对象,重置 result 对象为默认值
        result.seatCount = 0;
        result.boxOffice = 0;
    }





    return result;
};

export const chooseMember =async (name)=>{
    membersCount.value.member = 0
    membersCount.value.custom = 0
    const { data } = await getOrderAllInfo();

    // 初始化统计结果
    // 遍历data列表进行匹配和统计
    data.records.forEach((item) => {
        // 检查item_name是否匹配传入的name
        if (item.item_name === name) {
            // 根据payMethod分类统计
            if (item.payMethod === "member") {
                membersCount.value.member++
            } else {
                membersCount.value.custom++
            }
        }
    });
}









