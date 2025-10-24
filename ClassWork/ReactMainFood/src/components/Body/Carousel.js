import React, { useRef, useEffect } from 'react';

const Carousel = () => {
  const trackRef = useRef();

  const foodItems = [
    { name: 'Pizza', img: 'https://images.unsplash.com/photo-1601050690597-9d42ee52b9a9?auto=format&fit=crop&w=800&q=60' },
    { name: 'Burger', img: 'https://images.unsplash.com/photo-1565958011705-44e211f59e78?auto=format&fit=crop&w=800&q=60' },
    { name: 'Sushi', img: 'https://images.unsplash.com/photo-1551782450-a2132b4ba21d?auto=format&fit=crop&w=800&q=60' },
    { name: 'Pasta', img: 'https://images.unsplash.com/photo-1504674900247-0877df9cc836?auto=format&fit=crop&w=800&q=60' },
    { name: 'Salad', img: 'https://images.unsplash.com/photo-1546069901-ba9599a7e63c?auto=format&fit=crop&w=800&q=60' },
    { name: 'Ice Cream', img: 'https://images.unsplash.com/photo-1572448862528-5925c87032c5?auto=format&fit=crop&w=800&q=60' },
    { name: 'Fries', img: 'https://images.unsplash.com/photo-1601050690597-9d42ee52b9a9?auto=format&fit=crop&w=800&q=60' },
    { name: 'Lasagna', img: 'https://images.unsplash.com/photo-1504674900247-0877df9cc836?auto=format&fit=crop&w=800&q=60' },
    { name: 'Tacos', img: 'https://images.unsplash.com/photo-1529042410759-befb1204b468?auto=format&fit=crop&w=800&q=60' },
    { name: 'Dumplings', img: 'https://images.unsplash.com/photo-1604908177522-050e1ebf5a59?auto=format&fit=crop&w=800&q=60' },
    { name: 'Hot Dog', img: 'https://images.unsplash.com/photo-1565958011705-44e211f59e78?auto=format&fit=crop&w=800&q=60' },
    { name: 'Cake', img: 'https://images.unsplash.com/photo-1551782450-a2132b4ba21d?auto=format&fit=crop&w=800&q=60' },
  ];

  const scrollLeft = () => {
    const track = trackRef.current;
    track.scrollBy({ left: -track.clientWidth / 4, behavior: 'smooth' });
  };

  const scrollRight = () => {
    const track = trackRef.current;
    if (track.scrollLeft + track.clientWidth >= track.scrollWidth) {
      track.scrollTo({ left: 0, behavior: 'smooth' }); // infinite loop
    } else {
      track.scrollBy({ left: track.clientWidth / 4, behavior: 'smooth' });
    }
  };

  useEffect(() => {
    const track = trackRef.current;
    const interval = setInterval(scrollRight, 3000);

    const pauseScroll = () => clearInterval(interval);
    track.addEventListener('mouseenter', pauseScroll);
    track.addEventListener('mouseleave', () => setInterval(scrollRight, 3000));

    return () => clearInterval(interval);
  }, []);

  return (
    <section className="carousel-container">
      <h2>Popular Dishes</h2>
      <button className="carousel-btn left" onClick={scrollLeft}>&#10094;</button>
      <div className="carousel-track" ref={trackRef}>
        {foodItems.map((item, index) => (
          <div className="carousel-item" key={index}>
            <img src={item.img} alt={item.name} />
            <p>{item.name}</p>
          </div>
        ))}
      </div>
      <button className="carousel-btn right" onClick={scrollRight}>&#10095;</button>
    </section>
  );
};

export default Carousel;
