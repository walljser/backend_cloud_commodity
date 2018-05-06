package com.greyu.ysj.entity;

import java.util.ArrayList;
import java.util.List;

public class CategorySecondExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public CategorySecondExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    public String getOrderByClause() {
        return orderByClause;
    }

    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    public boolean isDistinct() {
        return distinct;
    }

    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        public Criteria andCategorySecondIdIsNull() {
            addCriterion("category_second_id is null");
            return (Criteria) this;
        }

        public Criteria andCategorySecondIdIsNotNull() {
            addCriterion("category_second_id is not null");
            return (Criteria) this;
        }

        public Criteria andCategorySecondIdEqualTo(Integer value) {
            addCriterion("category_second_id =", value, "categorySecondId");
            return (Criteria) this;
        }

        public Criteria andCategorySecondIdNotEqualTo(Integer value) {
            addCriterion("category_second_id <>", value, "categorySecondId");
            return (Criteria) this;
        }

        public Criteria andCategorySecondIdGreaterThan(Integer value) {
            addCriterion("category_second_id >", value, "categorySecondId");
            return (Criteria) this;
        }

        public Criteria andCategorySecondIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("category_second_id >=", value, "categorySecondId");
            return (Criteria) this;
        }

        public Criteria andCategorySecondIdLessThan(Integer value) {
            addCriterion("category_second_id <", value, "categorySecondId");
            return (Criteria) this;
        }

        public Criteria andCategorySecondIdLessThanOrEqualTo(Integer value) {
            addCriterion("category_second_id <=", value, "categorySecondId");
            return (Criteria) this;
        }

        public Criteria andCategorySecondIdIn(List<Integer> values) {
            addCriterion("category_second_id in", values, "categorySecondId");
            return (Criteria) this;
        }

        public Criteria andCategorySecondIdNotIn(List<Integer> values) {
            addCriterion("category_second_id not in", values, "categorySecondId");
            return (Criteria) this;
        }

        public Criteria andCategorySecondIdBetween(Integer value1, Integer value2) {
            addCriterion("category_second_id between", value1, value2, "categorySecondId");
            return (Criteria) this;
        }

        public Criteria andCategorySecondIdNotBetween(Integer value1, Integer value2) {
            addCriterion("category_second_id not between", value1, value2, "categorySecondId");
            return (Criteria) this;
        }

        public Criteria andCategoryFirstIdIsNull() {
            addCriterion("category_first_id is null");
            return (Criteria) this;
        }

        public Criteria andCategoryFirstIdIsNotNull() {
            addCriterion("category_first_id is not null");
            return (Criteria) this;
        }

        public Criteria andCategoryFirstIdEqualTo(Integer value) {
            addCriterion("category_first_id =", value, "categoryFirstId");
            return (Criteria) this;
        }

        public Criteria andCategoryFirstIdNotEqualTo(Integer value) {
            addCriterion("category_first_id <>", value, "categoryFirstId");
            return (Criteria) this;
        }

        public Criteria andCategoryFirstIdGreaterThan(Integer value) {
            addCriterion("category_first_id >", value, "categoryFirstId");
            return (Criteria) this;
        }

        public Criteria andCategoryFirstIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("category_first_id >=", value, "categoryFirstId");
            return (Criteria) this;
        }

        public Criteria andCategoryFirstIdLessThan(Integer value) {
            addCriterion("category_first_id <", value, "categoryFirstId");
            return (Criteria) this;
        }

        public Criteria andCategoryFirstIdLessThanOrEqualTo(Integer value) {
            addCriterion("category_first_id <=", value, "categoryFirstId");
            return (Criteria) this;
        }

        public Criteria andCategoryFirstIdIn(List<Integer> values) {
            addCriterion("category_first_id in", values, "categoryFirstId");
            return (Criteria) this;
        }

        public Criteria andCategoryFirstIdNotIn(List<Integer> values) {
            addCriterion("category_first_id not in", values, "categoryFirstId");
            return (Criteria) this;
        }

        public Criteria andCategoryFirstIdBetween(Integer value1, Integer value2) {
            addCriterion("category_first_id between", value1, value2, "categoryFirstId");
            return (Criteria) this;
        }

        public Criteria andCategoryFirstIdNotBetween(Integer value1, Integer value2) {
            addCriterion("category_first_id not between", value1, value2, "categoryFirstId");
            return (Criteria) this;
        }

        public Criteria andCategoryNameIsNull() {
            addCriterion("category_name is null");
            return (Criteria) this;
        }

        public Criteria andCategoryNameIsNotNull() {
            addCriterion("category_name is not null");
            return (Criteria) this;
        }

        public Criteria andCategoryNameEqualTo(String value) {
            addCriterion("category_name =", value, "categoryName");
            return (Criteria) this;
        }

        public Criteria andCategoryNameNotEqualTo(String value) {
            addCriterion("category_name <>", value, "categoryName");
            return (Criteria) this;
        }

        public Criteria andCategoryNameGreaterThan(String value) {
            addCriterion("category_name >", value, "categoryName");
            return (Criteria) this;
        }

        public Criteria andCategoryNameGreaterThanOrEqualTo(String value) {
            addCriterion("category_name >=", value, "categoryName");
            return (Criteria) this;
        }

        public Criteria andCategoryNameLessThan(String value) {
            addCriterion("category_name <", value, "categoryName");
            return (Criteria) this;
        }

        public Criteria andCategoryNameLessThanOrEqualTo(String value) {
            addCriterion("category_name <=", value, "categoryName");
            return (Criteria) this;
        }

        public Criteria andCategoryNameLike(String value) {
            addCriterion("category_name like", value, "categoryName");
            return (Criteria) this;
        }

        public Criteria andCategoryNameNotLike(String value) {
            addCriterion("category_name not like", value, "categoryName");
            return (Criteria) this;
        }

        public Criteria andCategoryNameIn(List<String> values) {
            addCriterion("category_name in", values, "categoryName");
            return (Criteria) this;
        }

        public Criteria andCategoryNameNotIn(List<String> values) {
            addCriterion("category_name not in", values, "categoryName");
            return (Criteria) this;
        }

        public Criteria andCategoryNameBetween(String value1, String value2) {
            addCriterion("category_name between", value1, value2, "categoryName");
            return (Criteria) this;
        }

        public Criteria andCategoryNameNotBetween(String value1, String value2) {
            addCriterion("category_name not between", value1, value2, "categoryName");
            return (Criteria) this;
        }

        public Criteria andImageIsNull() {
            addCriterion("image is null");
            return (Criteria) this;
        }

        public Criteria andImageIsNotNull() {
            addCriterion("image is not null");
            return (Criteria) this;
        }

        public Criteria andImageEqualTo(String value) {
            addCriterion("image =", value, "image");
            return (Criteria) this;
        }

        public Criteria andImageNotEqualTo(String value) {
            addCriterion("image <>", value, "image");
            return (Criteria) this;
        }

        public Criteria andImageGreaterThan(String value) {
            addCriterion("image >", value, "image");
            return (Criteria) this;
        }

        public Criteria andImageGreaterThanOrEqualTo(String value) {
            addCriterion("image >=", value, "image");
            return (Criteria) this;
        }

        public Criteria andImageLessThan(String value) {
            addCriterion("image <", value, "image");
            return (Criteria) this;
        }

        public Criteria andImageLessThanOrEqualTo(String value) {
            addCriterion("image <=", value, "image");
            return (Criteria) this;
        }

        public Criteria andImageLike(String value) {
            addCriterion("image like", value, "image");
            return (Criteria) this;
        }

        public Criteria andImageNotLike(String value) {
            addCriterion("image not like", value, "image");
            return (Criteria) this;
        }

        public Criteria andImageIn(List<String> values) {
            addCriterion("image in", values, "image");
            return (Criteria) this;
        }

        public Criteria andImageNotIn(List<String> values) {
            addCriterion("image not in", values, "image");
            return (Criteria) this;
        }

        public Criteria andImageBetween(String value1, String value2) {
            addCriterion("image between", value1, value2, "image");
            return (Criteria) this;
        }

        public Criteria andImageNotBetween(String value1, String value2) {
            addCriterion("image not between", value1, value2, "image");
            return (Criteria) this;
        }
    }

    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}