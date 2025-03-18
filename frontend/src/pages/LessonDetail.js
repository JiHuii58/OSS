import React, { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import styled from "styled-components";

const Container = styled.div`
  padding: 40px;
  background-color: #f9f9f9;
  min-height: 100vh;
  display: flex;
  flex-direction: column;
  align-items: center;
`;

const Title = styled.h2`
  color: #0077cc;
  text-align: center;
  font-size: 28px;
`;

const Content = styled.div`
  color: #333;
  font-size: 18px;
  width: 80%;
  max-width: 800px;
  line-height: 1.6;
  background: white;
  padding: 20px;
  border-radius: 10px;
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
  margin-bottom: 20px;
`;

const BackButton = styled.button`
  margin-top: 20px;
  padding: 10px 15px;
  font-size: 16px;
  border: none;
  background-color: #0077cc;
  color: white;
  border-radius: 5px;
  cursor: pointer;
  &:hover {
    background-color: #005fa3;
  }
`;

const QuestionContainer = styled.div`
  margin-top: 20px;
  width: 80%;
  max-width: 800px;
  background: white;
  padding: 20px;
  border-radius: 10px;
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
`;

const Question = styled.p`
  font-weight: bold;
  color: #333;
`;

const Option = styled.label`
  display: block;
  padding: 5px;
  cursor: pointer;
  &:hover {
    background-color: #f0f0f0;
  }
`;

const CorrectAnswer = styled.p`
  font-weight: bold;
  color: green;
  margin-top: 10px;
`;

const IncorrectAnswer = styled.p`
  font-weight: bold;
  color: red;
  margin-top: 10px;
`;

const LessonDetail = () => {
  const { id } = useParams();
  const [lesson, setLesson] = useState(null);
  const [reading, setReading] = useState(null);
  const [questions, setQuestions] = useState([]);
  const [error, setError] = useState(null);
  const [selectedAnswers, setSelectedAnswers] = useState({});
  const [showResults, setShowResults] = useState(false);

  useEffect(() => {
    const fetchLesson = async () => {
      try {
        const lessonResponse = await fetch(`http://localhost:8081/lessons/${id}`);
        if (!lessonResponse.ok) {
          throw new Error(`Lỗi API: ${lessonResponse.status} - ${lessonResponse.statusText}`);
        }
        const lessonData = await lessonResponse.json();
        setLesson(lessonData);
      } catch (err) {
        setError(`Lỗi khi tải bài học: ${err.message}`);
      }
    };

    const fetchReading = async () => {
      try {
        const token = localStorage.getItem("token"); // Lấy token từ localStorage
        console.log("🔑 Token trước khi gửi API:", token);
    
        const response = await fetch(`http://localhost:8081/lesson_readings/${id}`, {
          method: "GET",
          headers: {
            "Content-Type": "application/json",
            "Authorization": token ? `Bearer ${token}` : ""  // Đảm bảo gửi token
          }
        });
    
        if (!response.ok) {
          throw new Error(`Lỗi API: ${response.status} - ${response.statusText}`);
        }
    
        const data = await response.json();
        console.log("📥 Dữ liệu bài đọc nhận được:", data);
        setReading(data[0]); // Lấy bài đọc đầu tiên nếu có
      } catch (err) {
        console.error("🚨 Lỗi khi tải bài đọc:", err.message);
        setError(`Lỗi khi tải bài đọc: ${err.message}`);
      }
    };
    
    const fetchQuestions = async () => {
      try {
        const questionResponse = await fetch(`http://localhost:8081/lesson_questions/${id}`);
        if (!questionResponse.ok) {
          throw new Error(`Lỗi API: ${questionResponse.status} - ${questionResponse.statusText}`);
        }
        const questionData = await questionResponse.json();
        setQuestions(questionData);
      } catch (err) {
        setError(`Lỗi khi tải câu hỏi: ${err.message}`);
      }
    };

    fetchLesson();
    fetchReading();  // ✅ Gọi API bài đọc có Token
    fetchQuestions();
  }, [id]);

  const handleAnswerSelect = (questionIndex, option) => {
    setSelectedAnswers((prev) => ({ ...prev, [questionIndex]: option }));
  };

  const checkAnswers = () => {
    setShowResults(true);
  };

  if (error) {
    return <Container><p style={{ color: "red" }}>{error}</p></Container>;
  }

  return (
    <Container>
      {lesson ? (
        <>
          <Title>{lesson.title}</Title>

          {reading && (
            <Content>
              <h3>{reading.title}</h3>
              <p>{reading.content}</p>
            </Content>
          )}

          <h3>Câu hỏi ôn tập</h3>
          {questions.map((q, index) => (
            <QuestionContainer key={index}>
              <Question>{q.question}</Question>
              <Option>
                <input 
                  type="radio" 
                  name={`q${index}`} 
                  value="A" 
                  onChange={() => handleAnswerSelect(index, "A")} 
                  checked={selectedAnswers[index] === "A"}
                /> A. {q.optionA}
              </Option>
              <Option>
                <input 
                  type="radio" 
                  name={`q${index}`} 
                  value="B" 
                  onChange={() => handleAnswerSelect(index, "B")} 
                  checked={selectedAnswers[index] === "B"}
                /> B. {q.optionB}
              </Option>
              <Option>
                <input 
                  type="radio" 
                  name={`q${index}`} 
                  value="C" 
                  onChange={() => handleAnswerSelect(index, "C")} 
                  checked={selectedAnswers[index] === "C"}
                /> C. {q.optionC}
              </Option>
              <Option>
                <input 
                  type="radio" 
                  name={`q${index}`} 
                  value="D" 
                  onChange={() => handleAnswerSelect(index, "D")} 
                  checked={selectedAnswers[index] === "D"}
                /> D. {q.optionD}
              </Option>

              {showResults && (
                selectedAnswers[index] === q.correctOption ? 
                  <CorrectAnswer>✅ Đáp án đúng!</CorrectAnswer> : 
                  <IncorrectAnswer>❌ Đáp án sai! Đáp án đúng là: {q.correctOption}</IncorrectAnswer>
              )}
            </QuestionContainer>
          ))}

          <button onClick={checkAnswers} style={{ marginTop: "20px", padding: "10px", fontSize: "16px", cursor: "pointer" }}>
            Kiểm tra kết quả
          </button>

          <BackButton onClick={() => window.history.back()}>⬅ Quay lại</BackButton>
        </>
      ) : (
        <p>Đang tải bài học...</p>
      )}
    </Container>
  );
};

export default LessonDetail;
