import request from "@/utils/request.js"

/**
 * Created by  on  2024年4月23日
 */

// 上传视频文件
export const uploadVideo = (data) => {
    const formData = new FormData();
    // 将文件添加到 FormData 对象中
    formData.append('file', data);
    return request({
        method:"POST",
        url:"/boss/video/upload",
        data:formData
    })
}