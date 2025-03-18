import { useState } from "react";
import axios from "axios";
import { useNavigate } from "react-router-dom";
import { toast } from "react-toastify";

const RegisterPage = () => {
  const [name, setName] = useState("");
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const navigate = useNavigate();

  const handleRegister = async (e) => {
    e.preventDefault();
    try {
      const response = await axios.post("http://localhost:8081/users/register", {
        name,
        email,
        password,
      });

      if (response.status === 200) {
        toast.success("Đăng ký thành công!");
        navigate("/login");
      }
    } catch (error) {
      toast.error("Email đã tồn tại hoặc có lỗi!");
    }
  };

  return (
    <div>
      <h2>Đăng Ký</h2>
      <form onSubmit={handleRegister}>
        <input
          type="text"
          placeholder="Nhập tên"
          value={name}
          onChange={(e) => setName(e.target.value)}
          required
        />
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
        <button type="submit">Đăng ký</button>
      </form>
    </div>
  );
};

export default RegisterPage;
