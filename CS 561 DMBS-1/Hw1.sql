-- Query : 1
with base as 
(
	select cust,max(quant) as maxq,min(quant) as minq,avg(quant) as avg_quant
	from sales
	group by cust
), MAX_Q as
(
select s.cust,b.maxq as max_quant,s.prod as max_prod,s.day as max_day,s.month as max_month,s.year as max_year,s.state as max_state,b.avg_quant
from sales s,base b
where b.cust = s.cust and b.maxq = s.quant
), MIN_Q as
(
select s.cust,b.minq as min_quant,s.prod as min_prod,s.day as min_day,s.month as min_month,s.year as min_year,s.state as min_state
from sales s,base b
where b.cust = s.cust and b.minq = s.quant
)	
select mxq.cust, mxq.max_quant, mxq.max_prod,mxq.max_day,mxq.max_month,mxq.max_year,mxq.max_state
, mnq.min_quant, mnq.min_prod, mnq.min_day, mnq.min_month, mnq.min_year,mnq.min_state,mxq.avg_quant 
from MAX_Q as mxq, MIN_Q as mnq
where mxq.cust = mnq.cust


-- =============================================================== END OF QUERY 1 ==============================================================================

-- Query : 2
with base as
(
	select prod,month,sum(quant) as sq
	from sales
	group by prod,month
), base_q as
(
	select month,min(sq) as min_q,max(sq) as max_q
	from base
	group by month 
),least_popular_prod as
(
	select b.month,prod,min_q
	from base b, base_q bsq
	where b.month =bsq.month and b.sq = bsq.min_q
),most_popular_prod as
(
	select b.month,prod,max_q
	from base b, base_q bsq
	where b.month =bsq.month and b.sq = bsq.max_q
)

select lpp.month,lpp.prod as least_pop_prod, lpp.min_q as least_pop_total_q,
mpp.prod as most_pop_prod, mpp.max_q as most_pop_total_q
from least_popular_prod lpp,most_popular_prod mpp
where lpp.month =mpp.month
order by lpp.month


-- =============================================================== END OF QUERY 2 ==============================================================================

-- Query:3
with base as
(
	select prod,month,sum(quant) as sq
	from sales
	group by prod,month
), base_prod as
(
	select prod,min(sq) as min_q,max(sq) as max_q
	from base
	group by prod 
),least_fav_mo as
(
	select b.prod,month 
	from base b, base_prod as bprod
	where b.prod = bprod.prod and b.sq=bprod.min_q
), most_fav_mo as
(
	select b.prod,month 
	from base b, base_prod as bprod
	where b.prod = bprod.prod and b.sq=bprod.max_q
)

select lfm.prod, lfm.month as least_fav_mo, mfm.month as most_fav_mo
from least_fav_mo as lfm, most_fav_mo as mfm
where lfm.prod = mfm.prod


-- =============================================================== END OF QUERY 3 ==============================================================================

-- Query : 4
with base as
(
	select cust,prod,sum(quant),avg(quant),count(quant)
	from sales
	group by cust,prod
),Q1_avg as
(
	select cust,prod,avg(quant) a1
	from sales
	where month between 1 and 3
	group by cust,prod
)
,Q2_avg as
(
	select cust,prod,avg(quant) a2
	from sales
	where month between 4 and 6
	group by cust,prod
	order by cust
),Q3_avg as
(
	select cust,prod,avg(quant) a3
	from sales
	where month between 7 and 9
	group by cust,prod
),Q4_avg as
(
	select cust,prod,avg(quant) a4
	from sales
	where month between 10 and 12
	group by cust,prod
), Q12 as
(
	select avg_q1.cust,avg_q1.prod, avg_q1.a1,avg_q2.a2 
	from Q1_avg as avg_q1, Q2_avg as avg_q2
	where avg_q1.prod = avg_q2.prod and avg_q1.cust = avg_q2.cust
), Q34 as
(
	select avg_q3.cust,avg_q3.prod, avg_q3.a3,avg_q4.a4 
	from Q3_avg as avg_q3, Q4_avg as avg_q4
	where avg_q3.prod = avg_q4.prod and avg_q3.cust = avg_q4.cust
),final_avg as
(
	select Q12.cust,Q12.prod,Q12.a1,Q12.a2,Q34.a3,Q34.a4
	from Q12,Q34
	where Q12.prod =Q34.prod and Q12.cust =Q34.cust
)

select b.cust,b.prod,favg.a1 as Q1_avg,favg.a2 as Q2_avg,favg.a3 as Q3_avg,favg.a4 as Q4_avg,b.avg as average,b.sum as total,b.count 
from final_avg as favg,base b
where favg.prod = b.prod and favg.cust = b.cust
-- =============================================================== END OF QUERY 4 ==============================================================================
