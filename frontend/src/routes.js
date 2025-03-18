import { Routes, Route } from "react-router-dom";
import LoginPage from "./pages/LoginPage.js";
import RegisterPage from "./pages/RegisterPage.js";
import Dashboard from "./pages/Dashboard.js";
import Lessons from "./pages/Lessons.js";
import Vocabulary from "./pages/Vocabulary.js";
import LessonDetail from "./pages/LessonDetail.js";
import ListeningSpeaking from "./pages/ListeningSpeaking.js";

function AppRoutes() {
  return (
    <Routes>
      <Route path="/" element={<LoginPage />} />
      <Route path="/login" element={<LoginPage />} />
      <Route path="/register" element={<RegisterPage />} />
      <Route path="/dashboard" element={<Dashboard />} />
      <Route path="/lessons" element={<Lessons />} />
      <Route path="/vocabulary" element={<Vocabulary />} />
      <Route path="/lesson/:id" element={<LessonDetail />} />
      <Route path="/listening-speaking" element={<ListeningSpeaking />} />
    </Routes>
  );
}

export default AppRoutes;
