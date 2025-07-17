// src/components/Map.jsx

import React, { useState, useEffect, useRef } from 'react';

/**
 * 지도를 표시하는 컴포넌트
 * @param {number} lat - 중심 위도 (사용자 현재 위치)
 * @param {number} lon - 중심 경도 (사용자 현재 위치)
 * @param {object} destination - 목적지 좌표 객체 { lat, lon }
 */
const Map = ({ lat, lon, destination }) => {
    // 지도를 담을 div 요소를 참조합니다.
    const mapElement = useRef(null);
    // 생성된 카카오맵 객체를 저장할 state 입니다.
    const [map, setMap] = useState(null);
    // 목적지 마커 객체를 저장할 state 입니다.
    const [destinationMarker, setDestinationMarker] = useState(null);

    // 1. 처음 마운트될 때 지도를 생성하는 useEffect
    useEffect(() => {
        // kakao.maps 스크립트가 로드되지 않았다면 아무것도 하지 않습니다.
        if (!window.kakao) return;

        // 스크립트 로드가 완료된 후 지도를 생성합니다.
        window.kakao.maps.load(() => {
            const container = mapElement.current;
            const options = {
                center: new window.kakao.maps.LatLng(lat, lon),
                level: 4,
            };
            // 지도 객체를 생성하고 state에 저장합니다.
            const kakaoMap = new window.kakao.maps.Map(container, options);
            setMap(kakaoMap);

            // 실시간 교통정보 레이어를 추가합니다.
            kakaoMap.addOverlayMapTypeId(window.kakao.maps.MapTypeId.TRAFFIC);
        });
    }, [lat, lon]); // lat, lon이 처음 정해졌을 때 한 번만 실행됩니다.


    // 2. 목적지(destination)가 변경될 때마다 실행될 useEffect
    useEffect(() => {
        // 지도 객체나 목적지 좌표가 없으면 아무것도 하지 않습니다.
        if (!map || !destination) return;

        // 기존에 목적지 마커가 있다면 지도에서 제거합니다.
        if (destinationMarker) {
            destinationMarker.setMap(null);
        }

        // 새로운 목적지의 좌표 객체를 생성합니다.
        const destinationPosition = new window.kakao.maps.LatLng(destination.lat, destination.lon);

        // 새로운 목적지에 마커를 생성합니다.
        const newMarker = new window.kakao.maps.Marker({
            map: map,
            position: destinationPosition,
        });

        // 새로 생성한 마커를 state에 저장합니다. (나중에 또 지우기 위해)
        setDestinationMarker(newMarker);

        // 지도의 중심을 새로운 목적지로 부드럽게 이동시킵니다.
        map.panTo(destinationPosition);

    }, [map, destination]); // map 객체나 destination prop이 바뀔 때마다 실행됩니다.

    return (
        <div ref={mapElement} style={{
            width: '100%',
            maxWidth: '400px',
            height: '400px',
            borderRadius: '20px',
            marginTop: '20px'
        }} />
    );
};

export default Map;