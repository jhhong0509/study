# varchar(255) vs varchar(256)

기본적으로 varchar는 앞에 길이 정보를 담은 prefix를 가지고 있다.

만약 varchar의 size가 255를 넘어가게 되면 prefix가 1byte에서 2byte로 넘어가게 되기 때문에 기왕이면 255 이하로 잡는게 좋다.

