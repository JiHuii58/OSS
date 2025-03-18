import React, { useEffect, useState } from "react";
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
  color: #0077cc;
  text-align: center;
  font-size: 32px;
  margin-bottom: 20px;
`;

const TopicContainer = styled.div`
  display: flex;
  flex-wrap: wrap;
  gap: 10px;
  justify-content: center;
  margin-bottom: 20px;
`;

const TopicButton = styled.button`
  background-color: ${(props) => (props.active ? "#0077cc" : "#fff")};
  color: ${(props) => (props.active ? "#fff" : "#0077cc")};
  border: 2px solid #0077cc;
  padding: 8px 16px;
  font-size: 18px;
  border-radius: 20px;
  cursor: pointer;
  &:hover {
    background-color: #005fa3;
    color: white;
  }
`;

const FlashcardContainer = styled.div`
  perspective: 1000px;
  width: 300px;
  height: 200px;
`;

const Flashcard = styled.div`
  width: 100%;
  height: 100%;
  position: relative;
  transform-style: preserve-3d;
  transition: transform 0.5s;
  cursor: pointer;
  ${(props) => (props.flipped ? "transform: rotateY(180deg);" : "")}
`;

const FlashcardFront = styled.div`
  position: absolute;
  width: 100%;
  height: 100%;
  background-color: #0077cc;
  color: white;
  display: flex;
  justify-content: center;
  align-items: center;
  font-size: 28px;
  font-weight: bold;
  border-radius: 10px;
  backface-visibility: hidden;
`;

const FlashcardBack = styled(FlashcardFront)`
  background-color: #fff;
  color: #0077cc;
  transform: rotateY(180deg);
`;

const Controls = styled.div`
  display: flex;
  justify-content: center;
  gap: 10px;
  margin-top: 20px;
`;

const Button = styled.button`
  padding: 10px 15px;
  font-size: 18px;
  background-color: ${(props) => (props.primary ? "#0077cc" : "#ff4444")};
  color: white;
  border: none;
  border-radius: 5px;
  cursor: pointer;
  &:hover {
    background-color: ${(props) => (props.primary ? "#005fa3" : "#cc0000")};
  }
`;

const Vocabulary = () => {
  const [vocabulary, setVocabulary] = useState([]);
  const [currentWordIndex, setCurrentWordIndex] = useState(0);
  const [flipped, setFlipped] = useState(false);
  const [selectedTopic, setSelectedTopic] = useState(null);
  const [topics, setTopics] = useState([]);

  useEffect(() => {
    fetchVocabulary();
  }, []);

  const fetchVocabulary = async () => {
    try {
      const response = await fetch("http://localhost:8081/vocabulary");
      const data = await response.json();
      setVocabulary(data);
      const uniqueTopics = [...new Set(data.map((word) => word.topic))];
      setTopics(uniqueTopics);
    } catch (error) {
      console.error("Error fetching vocabulary:", error);
    }
  };

  const filterByTopic = (topic) => {
    setSelectedTopic(topic);
    setCurrentWordIndex(0);
    setFlipped(false);
  };

  const nextWord = () => {
    setFlipped(false);
    setCurrentWordIndex((prev) => (prev + 1) % filteredVocabulary.length);
  };

  const prevWord = () => {
    setFlipped(false);
    setCurrentWordIndex((prev) => (prev - 1 + filteredVocabulary.length) % filteredVocabulary.length);
  };

  const speakWord = () => {
    const word = filteredVocabulary[currentWordIndex]?.word;
    if (word) {
      const utterance = new SpeechSynthesisUtterance(word);
      utterance.lang = "en-US";
      speechSynthesis.speak(utterance);
    }
  };

  const filteredVocabulary = selectedTopic
    ? vocabulary.filter((word) => word.topic === selectedTopic)
    : vocabulary;

  if (filteredVocabulary.length === 0) {
    return (
      <Container>
        <Title>📚 Vocabulary Learning</Title>
        <p>No words available for this topic.</p>
      </Container>
    );
  }

  const currentWord = filteredVocabulary[currentWordIndex];

  return (
    <Container>
      <Title>📚 Vocabulary Learning</Title>

      <TopicContainer>
        {topics.map((topic) => (
          <TopicButton
            key={topic}
            active={selectedTopic === topic}
            onClick={() => filterByTopic(topic)}
          >
            {topic}
          </TopicButton>
        ))}
      </TopicContainer>

      <FlashcardContainer>
        <Flashcard flipped={flipped} onClick={() => setFlipped(!flipped)}>
          <FlashcardFront>{currentWord.word}</FlashcardFront>
          <FlashcardBack>{currentWord.meaning}</FlashcardBack>
        </Flashcard>
      </FlashcardContainer>

      <Controls>
        <Button primary onClick={prevWord} disabled={filteredVocabulary.length <= 1}>
          ⬅ Previous
        </Button>
        <Button onClick={speakWord}>🔊 Pronounce</Button>
        <Button primary onClick={nextWord} disabled={filteredVocabulary.length <= 1}>
          Next ➡
        </Button>
      </Controls>
    </Container>
  );
};

export default Vocabulary;
