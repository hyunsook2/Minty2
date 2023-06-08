    import axios from 'axios';
    import React, { useState, useEffect } from 'react';
    import { useParams } from 'react-router-dom';
    import { Container, Row, Col, Button, Carousel, Stack } from 'react-bootstrap';
    import '../css/boardDetail.css';
    import { formatDistanceToNow, parseISO } from 'date-fns';
    import { ko } from 'date-fns/locale';

    function BoardDetail({ csrfToken }) {
      let [tradeBoard, setTradeBoard] = useState({});
      let [imageList, setImageList] = useState([]);
      let [currentImage, setCurrentImage] = useState(0);
      const [nickName, setNickName] = useState('');
      const { id } = useParams();

      const fetchData = () => {
        axios
          .get(`/api/boardDetail/${id}`)
          .then((response) => {
            setTradeBoard(response.data.tradeBoard);
            setNickName(response.data.nickName);
            let list = [...response.data.imageList];
            setImageList(list);
          })
          .catch((e) => {
            console.error(e);
          });
      };

      useEffect(() => {
        fetchData();
      }, []);

      let timeAgo = '';
      if (tradeBoard.createdDate) {
        timeAgo = formatDistanceToNow(parseISO(tradeBoard.createdDate), { addSuffix: true, locale: ko });
      }

    const purchasingReq = () => {
      axios.post('/api/purchasingReq', tradeBoard, {
        headers: {
          "Content-Type": "application/json",
          "X-CSRF-TOKEN": csrfToken
        }
      })
      .then(response => {
         window.location.href= response.data;
      })
      .catch(e => {
        console.log(e);
      });
    };

      return (
        <Container>
            <Row className="board-top">
                      <Col className="overflow-container img-container">
                        <Carousel variant={'dark'} interval={null} slide={false} activeIndex={currentImage} onSelect={(index) => setCurrentImage(index)}>
                          {imageList.map((img) => (
                            <Carousel.Item key={img.id}>
                              <img
                                src={`https://storage.cloud.google.com/reboot-minty-storage/${img.imgUrl}`}
                                alt="Board Image"
                                className="board-img"
                              />
                            </Carousel.Item>
                          ))}
                        </Carousel>
                      </Col>
            <Col>
              <Stack gap={3}>
                <h2>{tradeBoard.title}</h2>
                <h2>{Number(tradeBoard.price).toLocaleString()} 원</h2>
                <h2>{nickName}</h2>
              </Stack>
              <Col className="board-stats">
                <span>🤍 {tradeBoard.interesting}</span>
                <span>👁‍ {tradeBoard.visit_count}</span>
                <span>{timeAgo}</span>
              </Col>
              <Col className="button-groups">
                <Button variant="primary">찜하기</Button>
                <Button variant="secondary">채팅</Button>
                <Button variant="success" onClick={purchasingReq}>구매 신청</Button>
              </Col>
            </Col>
          </Row>
          <br/><br/>
          <Row>
            <Col>
             {tradeBoard.content}
            </Col>
          </Row>
        </Container>
      );
    }

    export default BoardDetail;
