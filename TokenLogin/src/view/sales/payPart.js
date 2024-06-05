import {ref} from "vue"


export const payMountOfUse = ref(0)
export const payMountOfMovie = ref(0)


// 唤起支付页面
export const isPay = ref(false)


// 购物车
export const cart = ref([])

//总价
export const totalPriceOfcar = ref(0)

// 购物车的列表集合
export const selectedList = ref([])

watchEffect(() => {
    // 在 cart.value 发生变化时，更新 selectedList 的值
    selectedList.value = cart.value.map(item => ({
        id: item.product.id,
        totalAmount: totalPriceOfcar.value,
        item_total: item.product.quantity,
        item_name: item.product.name,
        item_type: item.category, // 获取商品类别
    }));
});


export const selectedSeats = ref([])  //选中位置


// 票的类型和价格
export const ticketType = ref({
    type:"",
    price:0,
})


// 已有的座位信息
export const seats = ref([])


// 电影信息
export const info = ref({
    name:"",
    id:0
})



export const isCash = ref(false)






