import {queryCondition,queryCourses,queryResult} from "@/composables/useCourses.js";



// 选好的电影

export const movieList = ref([])
export const getMovie=async ()=>{
    await queryCourses()

    if (hall.value === "") {
        movieList.value = queryResult.value.records.filter(item => item.discountTag === 'ENABLE')
    }else {
        movieList.value = queryResult.value.records.filter(item => item.discountTag === 'ENABLE' && item.sortNum === hall.value)
    }


}

// 影厅选择
export const hall = ref("")


// 解析数据
export const operation2=(data)=>{
    const parseRowColumn = (rowColumn) => {
        const [row, column] = rowColumn.split('-').map(Number);
        return [row, column];
    };

// 找到数据中的最大行和最大列
    let maxRow = 0;
    let maxColumn = 0;
    data.forEach((item) => {
        const [row, column] = parseRowColumn(item.row_column);
        maxRow = Math.max(maxRow, row);
        maxColumn = Math.max(maxColumn, column);
    });

// 初始化座位数组
    const seats = Array.from({ length: maxRow }, () => []);

// 将数据中的座位填充到座位数组中
    data.forEach((item) => {
        const [row, column] = parseRowColumn(item.row_column);
        seats[row - 1][column - 1] = {
            status: item.status,
            id: item.id
        };
    });

    return seats;

}



