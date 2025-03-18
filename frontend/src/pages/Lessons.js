import React, { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import styled from "styled-components";

const Container = styled.div`
  padding: 40px;
  background-color: #f4f4f4;
  min-height: 100vh;
  display: flex;
  flex-direction: column;
  align-items: center;
`;

const Title = styled.h2`
  color: #333;
  text-align: center;
  font-size: 28px;
  margin-bottom: 20px;
`;

const List = styled.div`
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(300px, 1fr));
  gap: 20px;
  width: 80%;
  max-width: 1200px;
`;

const LessonCard = styled.div`
  background: white;
  border-radius: 10px;
  padding: 20px;
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
  transition: transform 0.2s ease-in-out, box-shadow 0.2s ease-in-out;
  cursor: pointer;
  &:hover {
    transform: translateY(-5px);
    box-shadow: 0 8px 15px rgba(0, 0, 0, 0.2);
  }
`;

const LessonTitle = styled.h3`
  color: #0077cc;
  font-size: 20px;
  margin-bottom: 10px;
`;

const LessonInfo = styled.p`
  color: #555;
  font-size: 16px;
  margin: 5px 0;
`;

const ErrorMessage = styled.p`
  color: red;
  text-align: center;
  font-size: 18px;
`;

const Lessons = () => {
  const [lessons, setLessons] = useState([]);
  const [error, setError] = useState(null);
  const navigate = useNavigate();

  useEffect(() => {
    const fetchLessons = async () => {
      try {
        const response = await fetch("http://localhost:8081/lessons");

        if (!response.ok) {
          throw new Error(`Lỗi API: ${response.status} - ${response.statusText}`);
        }

        const data = await response.json();
        setLessons(data);
      } catch (err) {
        setError(err.message);
      }
    };

    fetchLessons();
  }, []);

  return (
    <Container>
      <Title>📚 Danh sách bài học</Title>
      {error && <ErrorMessage>{error}</ErrorMessage>}
      <List>
        {lessons.length > 0 ? (
          lessons.map((lesson) => (
            <LessonCard key={lesson.id} onClick={() => navigate(`/lesson/${lesson.id}`)}>
              <LessonTitle>{lesson.title}</LessonTitle>
              <LessonInfo>📌 Chủ đề: {lesson.topic}</LessonInfo>
              <LessonInfo>🎓 Cấp độ: {lesson.level}</LessonInfo>
            </LessonCard>
          ))
        ) : (
          <p>Không có bài học nào.</p>
        )}
      </List>
    </Container>
  );
};

export default Lessons;
