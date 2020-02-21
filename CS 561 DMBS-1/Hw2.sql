============================================================== Query 1 ========================================================================
	with base as
	(
		select cust,prod,state,round(avg(quant)) as sum
		from sales
		group by cust,prod,state
	),
	avg_cust as 
	(
		select s.prod,s.state,round(avg(s.quant)) as cavg
		from base b,sales s
		where b.cust<>s.cust 
		and b.prod = s.prod 
		and b.state = s.state
		group by s.prod,s.state
	),
	avg_prod as
	(
		select s.cust,s.state,round (avg(s.quant)) as pavg
		from base b, sales s
		where b.prod <> s.prod
		and b.cust = s.cust
		and b.state = s.state
		group by s.cust,s.state
	),
	join_avg_cust_base as
	(
		select b.cust,b.prod,b.state, round(ac.cavg) as cust_avg
		from base b, avg_cust ac
		where b.prod = ac.prod
		and b.state = ac.state
	),
	join_avg_prod_base as
	(
		select b.cust,b.state,b.prod,b.sum,ap.pavg as prod_avg
		from base b, avg_prod ap
		where b.cust = ap.cust
		and b.state = ap.state
	)

	select pb.cust,pb.prod,pb.state, pb.sum as total_avg, cb.cust_avg, pb.prod_avg 
	from join_avg_cust_base cb full outer join join_avg_prod_base pb
	on cb.cust = pb.cust
	and cb.state = pb.state
	and cb.prod = pb.prod
	order by cust,prod
============================================================ Query 2 ==================================================================================
with base1 as
(
	select distinct cust,prod 
	from sales
),
base2 as
(
	select distinct month
	from sales
),
info as
(
select *
from base1 cross join base2
order by cust,prod,month
	),
final_sales as
(
	select i.cust,i.prod,i.month, round(avg(s.quant)) as quant
	from info i natural left outer join sales s
    group by i.cust,i.prod,i.month
),after_month as
(
	select s.cust,s.prod,s.month,round(avg(s1.quant)) as avg_after
	from final_sales s left join final_sales s1
	on s.month = s1.month-1 and s.prod =s1.prod and s.cust = s1.cust
	group by s.cust,s.prod,s.month
	order by s.cust,s.prod,s.month
), before_month as
(
	select s.cust,s.prod,s.month,round(avg(s1.quant)) as avg_before
	from final_sales s left join final_sales s1
	on s.month = s1.month+1 and s.prod = s1.prod and s.cust = s1.cust
	group by s.cust,s.prod,s.month
	order by s.cust,s.prod,s.month
)

select am.cust, am.prod, am.month, am.avg_after, bm.avg_before 
from after_month am, before_month bm
where am.cust = bm.cust and am.prod = bm.prod and am.month = bm.month
order by am.month
============================================================ Query 3 ==================================================================================
with base as
(
	select distinct cust, prod, month
	from sales
	order by cust,prod,month
),
cumulative_sum as
(
	select s.cust, s.prod, b.month,sum(quant) as cm
	from base b, sales s
	where b.cust = s.cust
	and b.prod = s.prod
	and s.month <= b.month
	group by s.cust, s.prod,b.month
),
total_sum as
(
	select cust,prod,0.5*sum(quant) as ts
	from sales
	group by cust,prod
	order by cust,prod

),
info as
(
	select csum.cust,csum.prod,csum.month
	from cumulative_sum as csum, total_sum as tsum
	where csum.cust = tsum.cust 
	and csum.prod = tsum.prod 
	and csum.cm >= tsum.ts
	order by cust,prod,month
)

select cust,prod, min(month) as half_purchased_by_month
from info
group by cust,prod
order by cust,prod
