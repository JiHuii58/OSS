import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import styled from "styled-components";

const Container = styled.div`
  height: 100vh;
  display: flex;
  background: url("https://source.unsplash.com/1600x900/?learning,education") no-repeat center center fixed;
  background-size: cover;
  color: white;
  position: relative;
`;

const Sidebar = styled.div`
  width: 250px;
  background: rgba(0, 0, 0, 0.85);
  display: flex;
  flex-direction: column;
  align-items: center;
  padding: 30px 20px 20px;
  position: relative;
`;

const LogoContainer = styled.div`
  display: flex;
  align-items: center;
  gap: 10px;
  margin-bottom: 20px;
`;

const Logo = styled.img`
  width: 40px;
  height: 40px;
`;

const Title = styled.h2`
  color: #ffcc00;
  font-size: 20px;
  font-weight: bold;
`;

const Content = styled.div`
  flex: 1;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  position: relative;
`;

const WelcomeText = styled.h2`
  color: #ffcc00;
  font-size: 24px;
  font-weight: bold;
  position: absolute;
  top: 20px;
`;

const NavButton = styled.button`
  width: 100%;
  padding: 10px;
  margin: 5px 0;
  background: #555;
  color: white;
  border: none;
  text-align: left;
  cursor: pointer;
  display: flex;
  align-items: center;
  gap: 10px;
  &:hover {
    background: #777;
  }
`;

const Button = styled.button`
  background-color: #e74c3c;
  color: white;
  border: none;
  padding: 10px 15px;
  border-radius: 5px;
  cursor: pointer;
  margin-top: 10px;
  &:hover {
    background-color: #c0392b;
  }
`;

/* 🔥 Chatbox */
const ChatBoxContainer = styled.div`
  position: fixed;
  bottom: 20px;
  right: 20px;
  width: ${({ isOpen }) => (isOpen ? "300px" : "50px")};
  height: ${({ isOpen }) => (isOpen ? "400px" : "50px")};
  background: rgba(0, 0, 0, 0.8);
  border-radius: 10px;
  overflow: hidden;
  transition: all 0.3s ease-in-out;
`;

const ChatHeader = styled.div`
  background: #ffcc00;
  padding: 10px;
  text-align: center;
  cursor: pointer;
  font-weight: bold;
`;

const ChatMessages = styled.div`
  height: 300px;
  overflow-y: auto;
  padding: 10px;
  display: ${({ isOpen }) => (isOpen ? "block" : "none")};
`;

const ChatInput = styled.input`
  width: 100%;
  padding: 10px;
  border: none;
  outline: none;
  display: ${({ isOpen }) => (isOpen ? "block" : "none")};
`;

function Dashboard() {
  const navigate = useNavigate();
  const [user, setUser] = useState(null);
  const [chatOpen, setChatOpen] = useState(false);

  useEffect(() => {
    const storedUser = JSON.parse(localStorage.getItem("user"));
    const token = localStorage.getItem("token");

    if (!storedUser || !token) {
      navigate("/login");
    } else {
      setUser(storedUser);
    }
  }, [navigate]);

  useEffect(() => {
    const handlePopState = () => {
      const userCheck = JSON.parse(localStorage.getItem("user"));
      if (!userCheck) {
        navigate("/login");
      } else {
        setUser(userCheck); // ✅ Cập nhật lại state khi quay lại
      }
    };

    window.addEventListener("popstate", handlePopState);

    return () => {
      window.removeEventListener("popstate", handlePopState);
    };
  }, [navigate]);

  const handleLogout = () => {
    localStorage.removeItem("user");
    localStorage.removeItem("token");
    navigate("/login");
  };
  return (
    <Container>
      <Sidebar>
        {/* Thêm Logo + English Master */}
        <LogoContainer>
          <Logo src="/logo.jpg" alt="Logo" />
          <Title>English Master</Title>
        </LogoContainer>

        <h3>Xin chào, {user?.name || "Người dùng"}</h3>
        <NavButton onClick={() => navigate("/vocabulary")}>📚 Học từ vựng</NavButton>
        <NavButton onClick={() => navigate("/lessons")}>📖 Danh sách bài học</NavButton> {/* 🌟 MỚI THÊM */}
        <NavButton onClick={() => navigate("/listening-speaking")}>🎧 Luyện nghe & nói</NavButton>
        <NavButton onClick={() => navigate("/test-quizz")}>📝 Test & Quizz</NavButton>
        <NavButton onClick={() => navigate("/history")}>📜 Lịch sử học tập</NavButton>
        <NavButton onClick={() => navigate("/progress")}>📊 Tiến độ học tập</NavButton>

        <Button onClick={handleLogout}>Đăng xuất</Button>
      </Sidebar>
      <Content>
        <WelcomeText>Chào mừng {user?.name || "bạn"} đến với Dashboard</WelcomeText>
      </Content>

      {/* 🔥 Chatbox */}
      <ChatBoxContainer isOpen={chatOpen}>
        <ChatHeader onClick={() => setChatOpen(!chatOpen)}>💬 Chat với AI</ChatHeader>
        <ChatMessages isOpen={chatOpen}>
          <p>Chat AI sẽ được triển khai...</p>
        </ChatMessages>
        <ChatInput isOpen={chatOpen} type="text" placeholder="Nhập tin nhắn..." />
      </ChatBoxContainer>
    </Container>
  );
}

export default Dashboard;
