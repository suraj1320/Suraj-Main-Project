document.addEventListener('DOMContentLoaded', () => {
    // Simplified JS

    // Slider Logic
    const slider = document.querySelector('.slider');
    const slides = document.querySelectorAll('.slide');
    const prevBtn = document.querySelector('.prev-btn');
    const nextBtn = document.querySelector('.next-btn');
    let currentSlide = 0;

    if (slider && slides.length > 0) {
        function showSlide(index) {
            if (index >= slides.length) currentSlide = 0;
            else if (index < 0) currentSlide = slides.length - 1;
            else currentSlide = index;

            slider.style.transform = `translateX(-${currentSlide * 100}%)`;
        }

        if (nextBtn) nextBtn.addEventListener('click', () => {
            showSlide(currentSlide + 1);
        });

        if (prevBtn) prevBtn.addEventListener('click', () => {
            showSlide(currentSlide - 1);
        });

        // Auto Slide
        setInterval(() => {
            showSlide(currentSlide + 1);
        }, 5000); // 5 Seconds
    }

    // Toast logic
    function showToast(message) {
        let toast = document.getElementById("toast");
        if (!toast) {
            toast = document.createElement("div");
            toast.id = "toast";
            document.body.appendChild(toast);
        }
        toast.textContent = message;
        toast.className = "show";
        setTimeout(() => { toast.className = toast.className.replace("show", ""); }, 3000);
    }

    // Cart Logic
    const cartCountEl = document.querySelector('.cart-count');
    const addToCartBtns = document.querySelectorAll('.add-to-cart');

    function updateCartCount() {
        const cart = JSON.parse(localStorage.getItem('saga_cart')) || [];
        if (cartCountEl) cartCountEl.textContent = cart.length;
    }

    addToCartBtns.forEach(btn => {
        btn.addEventListener('click', (e) => {
            e.preventDefault();
            const id = btn.dataset.id;
            const title = btn.dataset.title;
            const price = btn.dataset.price;

            const cart = JSON.parse(localStorage.getItem('saga_cart')) || [];
            cart.push({ id, title, price });
            localStorage.setItem('saga_cart', JSON.stringify(cart));

            updateCartCount();
            showToast(`${title} added to cart`);
        });
    });

    updateCartCount();

    // Cart Page Logic (for cart.html)
    const cartItemsContainer = document.getElementById('cart-items');
    const emptyCartMsg = document.getElementById('empty-cart-msg');
    const cartContent = document.getElementById('cart-content');
    const cartTotalEl = document.getElementById('cart-total');
    const checkoutPageBtn = document.getElementById('checkout-btn');

    if (cartItemsContainer) {
        const cart = JSON.parse(localStorage.getItem('saga_cart')) || [];

        if (cart.length === 0) {
            emptyCartMsg.style.display = 'block';
            cartContent.style.display = 'none';
        } else {
            emptyCartMsg.style.display = 'none';
            cartContent.style.display = 'block';

            let total = 0;
            cartItemsContainer.innerHTML = '';

            cart.forEach((item, index) => {
                total += parseFloat(item.price);
                const tr = document.createElement('tr');
                tr.innerHTML = `
                    <td style="padding: 1rem 0; font-size: 1.1rem;">${item.title}</td>
                    <td style="text-align: right; padding: 1rem 0;">$${parseFloat(item.price).toFixed(2)}</td>
                    <td style="text-align: right; padding: 1rem 0;">
                        <button class="btn-danger" onclick="removeFromCart(${index})" style="padding: 5px 12px; font-size: 0.8rem;">Romove</button>
                    </td>
                `;
                cartItemsContainer.appendChild(tr);
            });

            cartTotalEl.textContent = '$' + total.toFixed(2);
        }
    }

    if (checkoutPageBtn) {
        checkoutPageBtn.onclick = () => {
            const cart = JSON.parse(localStorage.getItem('saga_cart')) || [];
            if (cart.length === 0) return;

            const items = cart.map(c => ({
                bookTitle: c.title,
                price: parseFloat(c.price),
                quantity: 1
            }));

            fetch('/checkout', {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify(items)
            })
                .then(response => {
                    if (response.redirected) {
                        window.location.href = response.url; // Login
                        return;
                    }
                    return response.text();
                })
                .then(text => {
                    if (text === "REDIRECT_LOGIN") {
                        window.location.href = "/login";
                    } else if (text === "SUCCESS") {
                        alert("Order Placed Successfully!");
                        localStorage.removeItem('saga_cart');
                        window.location.href = "/my-orders";
                    }
                })
                .catch(err => console.error(err));
        };
    }

    // Global Remove Function
    window.removeFromCart = function (index) {
        const cart = JSON.parse(localStorage.getItem('saga_cart')) || [];
        cart.splice(index, 1);
        localStorage.setItem('saga_cart', JSON.stringify(cart));
        window.location.reload(); // Refresh to update table
    };
});
