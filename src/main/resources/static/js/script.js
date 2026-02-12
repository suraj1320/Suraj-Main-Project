document.addEventListener('DOMContentLoaded', () => {
    // Simplified JS

    // Slider Logic
    const slider = document.querySelector('.slider');
    const slides = document.querySelectorAll('.slide');
    const prevBtn = document.querySelector('.prev-btn');
    const nextBtn = document.querySelector('.next-btn');
    let currentSlide = 0;

    // Force Clear Cart if version mismatch (To fix Book Not Found issues)
    const CURRENT_CART_VERSION = 'v3_fix_order';
    if (localStorage.getItem('cart_version') !== CURRENT_CART_VERSION) {
        localStorage.removeItem('saga_cart');
        localStorage.setItem('cart_version', CURRENT_CART_VERSION);
        console.log("Cart cleared due to version update.");
    }

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
        // Count total quantity items, not just distinct lines
        const totalCount = cart.reduce((acc, item) => acc + (item.quantity || 1), 0);
        if (cartCountEl) cartCountEl.textContent = totalCount;
    }

    // Use event delegation for add to cart
    // Use event delegation for add to cart
    // Expose global function for easier debugging and inline calls if needed
    window.addToCartGlobal = function (id, title, price, image) {
        console.log("Adding to cart (global):", id, title, price, image);
        let cart = JSON.parse(localStorage.getItem('saga_cart')) || [];
        const existingItemIndex = cart.findIndex(item => item.id == id); // loose comparison for string/number match
        if (existingItemIndex > -1) {
            cart[existingItemIndex].quantity = (cart[existingItemIndex].quantity || 1) + 1;
            // Update image if available (in case placeholder was there)
            if (image) cart[existingItemIndex].image = image;
        } else {
            cart.push({ id: id, title: title, price: price, quantity: 1, image: image });
        }
        localStorage.setItem('saga_cart', JSON.stringify(cart));
        updateCartCount(); // Fixed function name from updateCartCount to updateCartCount (it is correct)
        showToast(`${title} added to cart!`);
    };

    document.body.addEventListener('click', (e) => {
        if (e.target.classList.contains('add-to-cart')) {
            e.preventDefault();
            const btn = e.target;
            const id = btn.getAttribute('data-id');
            const title = btn.getAttribute('data-title');
            const price = parseFloat(btn.getAttribute('data-price'));

            // Call the global function
            window.addToCartGlobal(id, title, price);
        }
    });

    updateCartCount();

    // Cart Page Logic (for cart.html)
    const cartItemsContainer = document.getElementById('cart-items');
    const emptyCartMsg = document.getElementById('empty-cart-msg');
    const cartContent = document.getElementById('cart-content');
    const cartTotalEl = document.getElementById('cart-total');
    const checkoutPageBtn = document.getElementById('checkout-btn');

    if (cartItemsContainer) {
        renderCart();
    }

    function renderCart() {
        if (!cartItemsContainer) return;

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
                const qty = item.quantity || 1;
                const itemTotal = parseFloat(item.price) * qty;
                total += itemTotal;

                const tr = document.createElement('tr');
                tr.innerHTML = `
                    <td style="padding: 1rem 0; font-size: 1.1rem;">${item.title}</td>
                    <td style="text-align: right; padding: 1rem 0;">$${parseFloat(item.price).toFixed(2)}</td>
                    <td style="text-align: center; padding: 1rem 0;">
                        <button onclick="changeQuantity(${index}, -1)" style="padding: 2px 8px; font-weight:bold;">-</button>
                        <span style="margin: 0 10px;">${qty}</span>
                        <button onclick="changeQuantity(${index}, 1)" style="padding: 2px 8px; font-weight:bold;">+</button>
                    </td>
                    <td style="text-align: right; padding: 1rem 0;">$${itemTotal.toFixed(2)}</td>
                    <td style="text-align: right; padding: 1rem 0;">
                        <button class="btn-danger" onclick="removeFromCart(${index})" style="padding: 5px 12px; font-size: 0.8rem;">Remove</button>
                    </td>
                `;
                cartItemsContainer.appendChild(tr);
            });

            if (cartTotalEl) cartTotalEl.textContent = '$' + total.toFixed(2);
        }
    }

    window.changeQuantity = function (index, delta) {
        let cart = JSON.parse(localStorage.getItem('saga_cart')) || [];
        if (cart[index]) {
            cart[index].quantity = (cart[index].quantity || 1) + delta;
            if (cart[index].quantity < 1) cart[index].quantity = 1; // Min 1
            localStorage.setItem('saga_cart', JSON.stringify(cart));
            renderCart();
            updateCartCount();
        }
    };

    window.removeFromCart = function (index) {
        let cart = JSON.parse(localStorage.getItem('saga_cart')) || [];
        cart.splice(index, 1);
        localStorage.setItem('saga_cart', JSON.stringify(cart));
        renderCart();
        updateCartCount();
    };

    if (checkoutPageBtn) {
        checkoutPageBtn.onclick = () => {
            const cart = JSON.parse(localStorage.getItem('saga_cart')) || [];
            if (cart.length === 0) {
                alert("Your cart is empty!");
                return;
            }
            window.location.href = '/checkout/address';
        };
    }

    // Manual cart clear function
    window.clearCartManually = function () {
        if (confirm('Are you sure you want to clear your entire cart?')) {
            localStorage.removeItem('saga_cart');
            updateCartCount();
            if (cartItemsContainer) {
                renderCart();
            }
            alert('Cart cleared successfully!');
        }
    };
});
