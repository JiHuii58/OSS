import { useState, useEffect } from "react";
import axios from "axios";
import { useNavigate } from "react-router-dom";
import { toast, ToastContainer } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";

const LoginPage = () => {
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const navigate = useNavigate();

  useEffect(() => {
    const user = JSON.parse(localStorage.getItem("user"));
    if (user) {
      navigate("/dashboard");
    }
  }, [navigate]);

  const handleLogin = async (e) => {
    e.preventDefault();
    try {
      const response = await axios.post("http://localhost:8081/users/login", {
        email,
        password,
      });

      if (response.status === 200 && response.data.user) {
        localStorage.setItem("user", JSON.stringify(response.data.user));
        localStorage.setItem("token", response.data.token); // Lưu token để xác thực
        toast.success("Đăng nhập thành công!");
        
        setTimeout(() => {
          navigate("/dashboard");
        }, 1000);
      } else {
        toast.error("Lỗi đăng nhập. Vui lòng thử lại!");
      }
    } catch (error) {
      toast.error("Sai tài khoản hoặc mật khẩu!");
    }
  };

  return (
    <div>
      <h2>Đăng Nhập</h2>
      <form onSubmit={handleLogin}>
        <input
          type="email"
          placeholder="Nhập email"
          value={email}
          onChange={(e) => setEmail(e.target.value)}
          required
        />
        <input
          type="password"
          placeholder="Nhập mật khẩu"
          value={password}
          onChange={(e) => setPassword(e.target.value)}
          required
        />
        <button type="submit">Đăng nhập</button>
      </form>
      <ToastContainer />
    </div>
  );
};

export default LoginPage;
