package com.ledao.util;

/**
 * 分页工具类
 *
 * @author LeDao
 * @company
 * @create 2020-09-17 22:26
 */
public class PageUtil {

    /**
     * 生成分页代码
     *
     * @param targetUrl   目标地址
     * @param totalNum    总记录数
     * @param currentPage 当前页
     * @param pageSize    每页大小
     * @return
     */
    public static String genPagination1(String targetUrl, long totalNum, int currentPage, int pageSize, String param) {
        //当前页前后页的显示数量
        int pageTotal = 2;
        long totalPage = totalNum % pageSize == 0 ? totalNum / pageSize : totalNum / pageSize + 1;
        if (totalPage == 0) {
            return "未查询到数据";
        } else {
            StringBuffer pageCode = new StringBuffer();
            pageCode.append("<a href='" + targetUrl + "?page=1" + param + "'>首页</a>");
            if (currentPage > 1) {
                pageCode.append("<a href='" + targetUrl + "?page=" + (currentPage - 1) + param + "'>«</a>");
            } else {
                pageCode.append("<a>«</a>");
            }
            for (int i = currentPage - pageTotal; i <= currentPage + pageTotal; i++) {
                if (i < 1 || i > totalPage) {
                    continue;
                }
                if (i == currentPage) {
                    pageCode.append("<span>" + i + "</span>");
                } else {
                    pageCode.append("<a href='" + targetUrl + "?page=" + i + param + "'>" + i + "</a>");
                }
            }
            if (currentPage < totalPage) {
                pageCode.append("<a href='" + targetUrl + "?page=" + (currentPage + 1) + param + "'>»</a>");
            } else {
                pageCode.append("<a>»</a>");
            }
            pageCode.append("<a href='" + targetUrl + "?page=" + totalPage + param + "'>尾页</a>");
            return pageCode.toString();
        }
    }

    /**
     * 生成分页代码
     *
     * @param targetUrl   目标地址
     * @param totalNum    总记录数
     * @param currentPage 当前页
     * @param pageSize    每页大小
     * @return
     */
    public static String genPagination2(String targetUrl, long totalNum, int currentPage, int pageSize, String param) {
        //当前页前后页的显示数量
        int pageTotal = 2;
        long totalPage = totalNum % pageSize == 0 ? totalNum / pageSize : totalNum / pageSize + 1;
        if (totalPage == 0) {
            return "未查询到数据";
        } else {
            StringBuffer pageCode = new StringBuffer();
            if (currentPage == 1) {
                pageCode.append("<li><a>首页</a></li>");
            } else {
                pageCode.append("<li><a href='" + targetUrl + "?page=1" + param + "'>首页</a></li>");
            }
            if (currentPage > 1) {
                pageCode.append("<li><a class='status-icon hint--top' aria-label='上一页' href='" + targetUrl + "?page=" + (currentPage - 1) + param + "'>«</a></li>");
            } else {
                pageCode.append("<li><a>«</a></li>");
            }
            for (int i = currentPage - pageTotal; i <= currentPage + pageTotal; i++) {
                if (i < 1 || i > totalPage) {
                    continue;
                }
                if (i == currentPage) {
                    pageCode.append("<li class='active'><a>" + i + "</a></li>");
                } else {
                    pageCode.append("<li><a href='" + targetUrl + "?page=" + i + param + "'>" + i + "</a></li>");
                }
            }
            if (currentPage < totalPage) {
                pageCode.append("<li><a class='status-icon hint--top' aria-label='下一页' href='" + targetUrl + "?page=" + (currentPage + 1) + param + "'>»</a></li>");
            } else {
                pageCode.append("<li><a>»</a></li>");
            }
            if (currentPage == totalPage) {
                pageCode.append("<li><a>尾页</a></li>");
            } else {
                pageCode.append("<li><a href='" + targetUrl + "?page=" + totalPage + param + "'>尾页</a></li>");
            }
            return pageCode.toString();
        }
    }

    /**
     * 生成分页代码
     *
     * @param targetUrl   目标地址
     * @param totalNum    总记录数
     * @param currentPage 当前页
     * @param pageSize    每页大小
     * @return
     */
    public static String genPagination3(String targetUrl, long totalNum, int currentPage, int pageSize, String param) {
        //当前页前后页的显示数量
        int pageTotal = 2;
        long totalPage = totalNum % pageSize == 0 ? totalNum / pageSize : totalNum / pageSize + 1;
        if (totalPage == 0) {
            return "未查询到数据";
        } else {
            StringBuffer pageCode = new StringBuffer();
            pageCode.append("<li class='page-item'><a class='page-link' href='" + targetUrl + "?page=1" + param + "'>首页</a></li>");
            if (currentPage > 1) {
                pageCode.append("<li class='page-item'><a class='page-link' href='" + targetUrl + "?page=" + (currentPage - 1) + param + "'>«</a></li>");
            } else {
                pageCode.append("<li class='page-item'><a class='page-link'>«</a></li>");
            }
            for (int i = currentPage - pageTotal; i <= currentPage + pageTotal; i++) {
                if (i < 1 || i > totalPage) {
                    continue;
                }
                if (i == currentPage) {
                    pageCode.append("<li class='page-item'><a class='page-link' style='background-color: #ff1d5b;color: white'>" + i + "</a></li>");
                } else {
                    pageCode.append("<li class='page-item'><a class='page-link' href='" + targetUrl + "?page=" + i + param + "'>" + i + "</a></li>");
                }
            }
            if (currentPage < totalPage) {
                pageCode.append("<li class='page-item'><a class='page-link' href='" + targetUrl + "?page=" + (currentPage + 1) + param + "'>»</a></li>");
            } else {
                pageCode.append("<li class='page-item'><a class='page-link'>»</a></li>");
            }
            pageCode.append("<li class='page-item'><a class='page-link' href='" + targetUrl + "?page=" + totalPage + param + "'>尾页</a></li>");
            return pageCode.toString();
        }
    }

    /**
     * 生成分页代码
     *
     * @param targetUrl   目标地址
     * @param totalNum    总记录数
     * @param currentPage 当前页
     * @param pageSize    每页大小
     * @return
     */
    public static String genPagination4(String targetUrl, long totalNum, int currentPage, int pageSize, String param) {
        //当前页前后页的显示数量
        int pageTotal = 2;
        long totalPage = totalNum % pageSize == 0 ? totalNum / pageSize : totalNum / pageSize + 1;
        if (totalPage == 0) {
            return "未查询到数据";
        } else {
            StringBuffer pageCode = new StringBuffer();
            if (currentPage == 1) {
                pageCode.append("<a><div class='ui primary basic button'>首页</div></a>");
            } else {
                pageCode.append("<a href='" + targetUrl + "?page=1" + param + "'><div class='ui primary basic button'>首页</div></a>");
            }
            if (currentPage > 1) {
                pageCode.append("<a href='" + targetUrl + "?page=" + (currentPage - 1) + param + "'><div class='ui primary basic button'>«</div></a>");
            } else {
                pageCode.append("<a><div class='ui primary basic button'>«</div></a>");
            }
            for (int i = currentPage - pageTotal; i <= currentPage + pageTotal; i++) {
                if (i < 1 || i > totalPage) {
                    continue;
                }
                if (i == currentPage) {
                    pageCode.append("<a><div class='ui button primary '>" + i + "</div></a>");
                } else {
                    pageCode.append("<a href='" + targetUrl + "?page=" + i + param + "'><div class='ui primary basic button'>" + i + "</div></a>");
                }
            }
            if (currentPage < totalPage) {
                pageCode.append("<a href='" + targetUrl + "?page=" + (currentPage + 1) + param + "'><div class='ui primary basic button'>»</div></a>");
            } else {
                pageCode.append("<a><div class='ui primary basic button'>»</div></a>");
            }
            if (currentPage == totalPage) {
                pageCode.append("<a><div class='ui primary basic button'>尾页</div></a>");
            } else {
                pageCode.append("<a href='" + targetUrl + "?page=" + totalPage + param + "'><div class='ui primary basic button'>尾页</div></a>");
            }
            return pageCode.toString();
        }
    }
}
