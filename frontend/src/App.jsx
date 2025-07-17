import { useState, useEffect } from 'react';
import axios from 'axios';
import './App.css'; // 잠시 후 추가할 CSS 파일입니다.
import Map from './components/Map.jsx';

function App() {
    // 날씨 데이터를 저장할 state (이제 단순 문자열이 아닌 객체입니다)
    const [regionData, setRegionData] = useState(null);
    // 로딩 상태를 관리할 state
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);
    const [coords, setCoords] = useState({ lat: null, lon: null });

    const [searchQuery, setSearchQuery] = useState('');
    const [destinationCoords, setDestinationCoords] = useState(null);

    useEffect(() => {
        navigator.geolocation.getCurrentPosition(
            (position) => {
                const lat = position.coords.latitude;
                const lon = position.coords.longitude;
                setCoords({ lat, lon });

                axios.get(`/api/region-info?lat=${lat}&lon=${lon}`)
                    .then(response => {
                        setRegionData(response.data);
                    })
                    .catch(err => {
                        console.error("지역 정보를 가져오는 중 오류 발생:", err);
                        setError("지역 정보를 불러오는 데 실패했습니다.");
                    })
                    .finally(() => {
                        setLoading(false);
                    });
            },
            (err) => {
                console.error("위치 정보를 가져오는 중 오류 발생:", err);
                setError("위치 정보를 가져올 수 없습니다. 브라우저의 위치 정보 접근을 허용해주세요.");
                setLoading(false);
            }
        );
    }, []);// []를 비워두면 컴포넌트가 처음 렌더링될 때 한 번만 실행됩니다.

    const handleSearch = () => {
        // 검색어가 비어있으면 함수를 종료합니다.
        if (!searchQuery) {
            alert('목적지를 입력해주세요.');
            return;
        }

        axios.get(`/api/search?query=${searchQuery}`)
            .then(response => {
                // 응답 데이터가 있다면
                if (response.data) {
                    // 검색된 목적지의 좌표를 만듭니다. (카카오는 x가 경도, y가 위도)
                    const newDestination = {
                        lat: response.data.y,
                        lon: response.data.x
                    };
                    // 목적지 좌표 state를 업데이트합니다.
                    setDestinationCoords(newDestination);
                    // (디버깅용) 콘솔에 목적지 좌표를 출력합니다.
                    console.log("검색된 목적지 좌표:", newDestination);
                    alert(`'${searchQuery}' 검색 성공!`);
                } else {
                    alert('검색 결과가 없습니다.');
                }
            })
            .catch(err => {
                console.error("주소 검색 중 오류 발생:", err);
                alert('주소 검색 중 오류가 발생했습니다.');
            });
    };

    const getAqiString = (aqi) => {
        switch (aqi){
            case 1: return "매우 좋음";
            case 2: return "매우 좋음";
            case 3: return "보통";
            case 4: return "나쁨";
            case 5: return "매우 나쁨";
            default: return "알 수 없음";
        }
    };

    // 로딩 중일 때 보여줄 화면
    if (loading) return <div>위치 및 환경 정보를 불러오는 중...</div>;

    if (error) return <div>오류: {error}</div>;

    // 날씨 데이터가 없을 때 (에러 발생 등)
    if (!regionData || !regionData.weather || !regionData.airPollution) return <div>데이터가 없습니다.</div>;

    // 데이터에 접근하기 쉽도록 weather와 airPollution을 분리합니다.
    const { weather, airPollution } = regionData;

    // 성공적으로 데이터를 받아왔을 때 보여줄 화면
    return (
        <div className="container">

            {/* ▼▼▼ 검색창 UI를 추가합니다. ▼▼▼ */}
            <div className="search-box">
                <input
                    type="text"
                    value={searchQuery}
                    onChange={(e) => setSearchQuery(e.target.value)}
                    placeholder="목적지를 입력하세요 (예: 판교역)"
                />
                <button onClick={handleSearch}>검색</button>
            </div>

        <div className="card">
            {/* 도시 이름: weatherData 객체의 name 속성 */}
            <h1>{weather.name}의 현재 날씨</h1>

            {/* 날씨 정보 섹션 */}
            <div className="section">
                <h2>날씨</h2>
                <div className="info-grid">
                    {/* 날씨 아이콘: weather 배열의 첫 번째 요소의 icon 속성 */}
                    <img
                        src={`https://openweathermap.org/img/wn/${weather.weather[0].icon}@2x.png`}
                        alt={weather.weather[0].description}
                    />
                    {/* 온도: main 객체의 temp 속성 */}
                    <p className="temperature">{weather.main.temp}°C</p>
                    {/* 날씨 설명: weather 배열의 첫 번째 요소의 description 속성 */}
                    <p className="description">{weather.weather[0].description}</p>
                </div>
            </div>

            {/* 대기 질 정보 섹션 */}
            <div className="section">
                <h2>대기 질</h2>
                <div className="air-quality-info">
                    <p><strong>통합 지수:</strong> {getAqiString(airPollution.list[0].main.aqi)}</p>
                    <p><strong>초미세먼지(PM2.5):</strong> {airPollution.list[0].components.pm2_5} µg/m³</p>
                    <p><strong>미세먼지(PM10):</strong> {airPollution.list[0].components.pm10} µg/m³</p>
                </div>
            </div>
        </div>
    {/* 좌표가 있을 때만 Map 컴포넌트를 렌더링 합니다. */}
    {coords.lat && coords.lon && (
        <Map
            lat={coords.lat}
            lon={coords.lon}
            destination={destinationCoords}
        />
    )}
    </div>
    );
}

export default App;