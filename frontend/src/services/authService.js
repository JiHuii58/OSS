import axios from "axios";

const API_URL = "http://localhost:8081";

export const loginUser = async (userData) => {
  try {
    const response = await axios.post(`${API_URL}/users/login`, userData, {
      headers: { "Content-Type": "application/json" }
    });

    if (!response.data || !response.data.user) {
      throw new Error(response.data.error || "Lỗi đăng nhập: Không nhận được dữ liệu user.");
    }

    // ✅ Lưu user và token nếu có
    localStorage.setItem("user", JSON.stringify(response.data.user));
    localStorage.setItem("token", response.data.token || "");

    return response.data.user;
  } catch (error) {
    console.error("Lỗi khi đăng nhập:", error.response?.data || error.message);
    throw error;
  }
};



export const registerUser = async (userData) => {
  try {
    const response = await axios.post(`${API_URL}/users/register`, userData, {
      headers: { "Content-Type": "application/json" }
    });

    if (!response.data) {
      throw new Error("Phản hồi từ máy chủ không hợp lệ");
    }

    if (response.data.error) {
      throw new Error(response.data.error);
    }

    return response.data;
  } catch (error) {
    console.error("Lỗi khi đăng ký:", error.response?.data || error.message);
    throw error;
  }
};
