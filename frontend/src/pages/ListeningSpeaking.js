import React, { useEffect, useState } from "react";
import axios from "axios";

const ListeningSpeaking = () => {
  const [lessons, setLessons] = useState([]);
  const [selectedLesson, setSelectedLesson] = useState(null);
  const [questions, setQuestions] = useState([]);
  const [currentQuestion, setCurrentQuestion] = useState(0);
  const [userAnswer, setUserAnswer] = useState("");
  const [isListening, setIsListening] = useState(false);
  const [feedback, setFeedback] = useState("");
  const [speed, setSpeed] = useState(1); // Tốc độ phát video
  const recognitionRef = React.useRef(null);

  // Lấy danh sách bài nghe từ API
  useEffect(() => {
    axios
      .get("http://localhost:8081/listening_lessons")
      .then((response) => setLessons(response.data))
      .catch((error) => console.error("Lỗi lấy bài nghe:", error));
  }, []);

  // Load bài nghe và câu hỏi
  const loadLesson = (lessonId) => {
    axios
      .get(`http://localhost:8081/listening_questions/lesson/${lessonId}`)
      .then((response) => {
        setSelectedLesson(lessons.find((lesson) => lesson.id === lessonId));
        setQuestions(response.data);
        setCurrentQuestion(0);
        setUserAnswer("");
        setFeedback("");
      })
      .catch((error) => console.error("Lỗi lấy câu hỏi:", error));
  };

  // Nhận dạng giọng nói
  const startListening = () => {
    if (!window.SpeechRecognition && !window.webkitSpeechRecognition) {
      alert("Trình duyệt của bạn không hỗ trợ nhận dạng giọng nói!");
      return;
    }

    const SpeechRecognition =
      window.SpeechRecognition || window.webkitSpeechRecognition;
    const recognition = new SpeechRecognition();
    recognition.lang = "en-US";
    recognition.interimResults = false;
    recognition.maxAlternatives = 1;

    recognition.onstart = () => setIsListening(true);
    recognition.onresult = (event) => {
      const transcript = event.results[0][0].transcript;
      setUserAnswer(transcript);
      checkAnswer(transcript);
    };
    recognition.onerror = (event) => console.error("Lỗi nhận dạng:", event.error);
    recognition.onend = () => setIsListening(false);

    recognitionRef.current = recognition;
    recognition.start();
  };

  // Kiểm tra đáp án
  const checkAnswer = (answer) => {
    if (questions.length === 0) return;
    
    const correctAnswer = questions[currentQuestion]?.correctAnswer.toLowerCase().trim();
    if (answer.toLowerCase().trim() === correctAnswer) {
      setFeedback("✅ Đúng!");
    } else {
      setFeedback(`❌ Sai! Đáp án đúng: ${correctAnswer}`);
    }
  };

  return (
    <div style={{ padding: "20px", maxWidth: "800px", margin: "auto" }}>
      <h2>🎧 Luyện Nghe & Nói</h2>

      {/* Danh sách bài học */}
      {!selectedLesson && (
        <div>
          <h3>Chọn một bài nghe:</h3>
          {lessons.map((lesson) => (
            <button
              key={lesson.id}
              style={{ margin: "5px", padding: "10px", cursor: "pointer" }}
              onClick={() => loadLesson(lesson.id)}
            >
              {lesson.title}
            </button>
          ))}
        </div>
      )}

      {/* Hiển thị video + câu hỏi */}
      {selectedLesson && (
        <div>
          <h3>{selectedLesson.title}</h3>
          <iframe
            width="100%"
            height="400px"
            src={`${selectedLesson.videoUrl.replace("watch?v=", "embed/")}?playbackRate=${speed}`}
            title="Listening Video"
            frameBorder="0"
            allowFullScreen
          ></iframe>

          {/* Điều chỉnh tốc độ video */}
          <div style={{ marginTop: "10px" }}>
            <label>Tốc độ phát:</label>
            <select value={speed} onChange={(e) => setSpeed(e.target.value)}>
              <option value="0.5">0.5x</option>
              <option value="1">1x (Bình thường)</option>
              <option value="1.5">1.5x</option>
              <option value="2">2x</option>
            </select>
          </div>

          {/* Hiển thị câu hỏi điền từ */}
          {questions.length > 0 && currentQuestion < questions.length && (
            <div style={{ marginTop: "20px" }}>
              <h4>Điền từ còn thiếu:</h4>
              <p>{questions[currentQuestion]?.question.replace("_____", "______")}</p>
              <input
                type="text"
                value={userAnswer}
                onChange={(e) => setUserAnswer(e.target.value)}
                placeholder="Nhập hoặc nói câu trả lời..."
                style={{ padding: "10px", width: "100%", marginBottom: "10px" }}
              />
              <button
                onClick={() => checkAnswer(userAnswer)}
                style={{ marginRight: "10px", padding: "10px", cursor: "pointer" }}
              >
                Kiểm tra
              </button>
              <button
                onClick={startListening}
                disabled={isListening}
                style={{ padding: "10px", cursor: "pointer" }}
              >
                {isListening ? "🎙️ Đang nghe..." : "🎤 Nói"}
              </button>
              <p>{feedback}</p>

              {/* Chuyển sang câu tiếp theo */}
              <button
                onClick={() => {
                  setCurrentQuestion((prev) => prev + 1);
                  setUserAnswer("");
                  setFeedback("");
                }}
                disabled={currentQuestion >= questions.length - 1}
                style={{ marginTop: "10px", padding: "10px", cursor: "pointer" }}
              >
                Câu tiếp theo ➡️
              </button>
            </div>
          )}

          {/* Quay lại danh sách bài học */}
          {currentQuestion >= questions.length && (
            <button
              onClick={() => setSelectedLesson(null)}
              style={{ marginTop: "20px", padding: "10px", cursor: "pointer" }}
            >
              🔙 Quay lại danh sách bài học
            </button>
          )}
        </div>
      )}
    </div>
  );
};

export default ListeningSpeaking;
