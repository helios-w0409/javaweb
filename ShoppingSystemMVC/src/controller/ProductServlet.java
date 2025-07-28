
@WebServlet("/ProductServlet")
public class ProductServlet extends HttpServlet {
    private ProductService service = new ProductService();

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int page = Integer.parseInt(req.getParameter("page"));
        int limit = Integer.parseInt(req.getParameter("limit"));
        List<Product> products = service.getProductsByPage(page, limit);
        Gson gson = new Gson();
        resp.setContentType("application/json;charset=UTF-8");
        resp.getWriter().write(gson.toJson(products));
    }
}
