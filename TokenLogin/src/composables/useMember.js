import {getMemberHistoryConsume, getMemberInfo} from "@/api/member"
// 验证结果
export const isSaved = ref(false)


export const form=ref({})

// 验证会员是否存在
export const checkMember = async (member) => {
    const {data} = await getMemberInfo(member)
    isSaved.value = data.code === "000000";
}




// 查询结果
export const memberList = ref({})
// 查询条件
export const searchForm = ref({})

// 会员查询搜索分页 方法
export const searchMember = async (param={}) => {
    Object.assign(searchForm.value, param)
    const {data} = await getMemberHistoryConsume(searchForm.value)
    memberList.value = data.data
    console.log(memberList.value)

}




