import{m as G,n as L,o as H}from"./index-BPolhdD0.js";import{b as J,a as k,r as v,o as K,c as Q,q as e,m as t,n as p,i as $,g as n,u as g,t as s,e as X,l as Y,y as Z,k as ee}from"./index-BUNzSB4e.js";const te={class:"dialog-footer"},re=J({__name:"Viewing",setup(le){const{proxy:u}=ee();let f=k({name:"",page:1,pageSize:10}),z=v(1),m=k([]);v("https://fuss10.elemecdn.com/e/5d/4a731a90594a4af544c0c25941171jpeg.jpeg");const C=v(),b=v(!1),l=k({});async function w(){u.$startLoading();const o=await G(f);if(u.$closeLoading(),o.code!==1)return u.$message.error("获取列表失败");m.splice(0,m.length,...o.data.records),z.value=o.data.total,m.forEach(a=>{a.size=a.size.replace(/;/g,`
`),a.userInfo=a.userInfo.replace(/;/g,`
`)})}const _=/(?:巡检人|会审专家|维修人员)\s?: *(.*?); *电话: *(\d+)/g,I=o=>{f.pageSize=o,w()},T=o=>{f.page=o,w()};function N(o,a){return a.riskLevel===o}function S(o,a){return a.defectType===o}async function V(o){u.$startLoading();const a=await L(o.id);if(u.$closeLoading(),console.log(a),a.code!==1)return u.$message.error("服务器故障");Object.assign(l,a.data),_.lastIndex=0;let i=_.exec(l.inspectedInfo);i&&(l.inspectedName=i[1].trim(),l.inspectedPhone=i[2]),_.lastIndex=0,i=_.exec(l.expertInfo),i&&(l.expertName=i[1].trim(),l.expertPhone=i[2]),_.lastIndex=0,i=_.exec(l.workerInfo),i&&(l.workerName=i[1].trim(),l.workerPhone=i[2]),b.value=!0}function U(o){return o.riskLevel==="一级风险"?"info":o.riskLevel==="二级风险"?"warning":"danger"}const P=async o=>{u.$startLoading();const a=await L(o.id);if(u.$closeLoading(),a.code!==1)return u.$message.error("服务器故障");u.$startLoading();const i=await H(a.data.id);if(u.$closeLoading(),i.code!==1)return u.$message.error("删除失败");u.$message.success("已删除"),w()},j=()=>{C.value.clearFilter()};return K(()=>{w()}),(o,a)=>{const i=p("el-breadcrumb-item"),B=p("el-breadcrumb"),h=p("el-button"),D=p("el-col"),R=p("el-row"),c=p("el-table-column"),q=p("el-tag"),y=p("el-image"),E=p("el-popconfirm"),F=p("el-table"),M=p("el-pagination"),O=p("el-card"),d=p("el-descriptions-item"),x=p("el-descriptions"),W=p("el-dialog");return $(),Q("div",null,[e(B,{separator:"/"},{default:t(()=>[e(i,{to:{path:"/home"}},{default:t(()=>[n("首页")]),_:1}),e(i,null,{default:t(()=>[n("系统管理")]),_:1}),e(i,null,{default:t(()=>[n("机构管理")]),_:1})]),_:1}),e(O,null,{default:t(()=>[e(R,{gutter:20},{default:t(()=>[e(D,{span:5},{default:t(()=>[e(h,{onClick:j},{default:t(()=>[n("重置过滤器")]),_:1})]),_:1})]),_:1}),e(F,{ref_key:"tableRef",ref:C,data:g(m),stripe:"",border:"",fit:"",style:{width:"100%"}},{default:t(()=>[e(c,{fixed:"",type:"index",sortable:"",label:"序号",align:"center",width:"100"}),e(c,{prop:"id",label:"id",align:"center",width:"300"}),e(c,{prop:"size",label:"图片大小",align:"center",width:"250"}),e(c,{prop:"userInfo",label:"用户信息",align:"center",width:"150"}),e(c,{prop:"defectType",filters:[{text:"裂痕",value:"裂痕"},{text:"物理损坏",value:"物理损坏"},{text:"电气损坏",value:"电气损坏"},{text:"脏污",value:"脏污"},{text:"积雪覆盖",value:"积雪覆盖"},{text:"清洁",value:"清洁"}],label:"缺陷类型",align:"center",width:"150","filter-method":S}),e(c,{prop:"riskLevel",filters:[{text:"一级风险",value:"一级风险"},{text:"二级风险",value:"二级风险"},{text:"三级风险",value:"三级风险"}],label:"风险等级",align:"center",width:"150","filter-method":N},{default:t(r=>[e(q,{type:U(r.row),"disable-transitions":""},{default:t(()=>[n(s(r.row.riskLevel),1)]),_:2},1032,["type"])]),_:1}),e(c,{prop:"instrumentType",label:"设备类型",align:"center",width:"150"}),e(c,{prop:"uploadType",label:"来源",align:"center",width:"100"}),e(c,{label:"原始图片",width:"100"},{default:t(r=>[e(y,{style:{width:"60px",height:"60px"},src:r.row.originalUrl,"preview-src-list":[r.row.originalUrl]},null,8,["src","preview-src-list"])]),_:1}),e(c,{label:"分析后图片",width:"100"},{default:t(r=>[e(y,{style:{width:"60px",height:"60px"},src:r.row.analyzedUrl,"preview-src-list":[r.row.analyzedUrl]},null,8,["src","preview-src-list"])]),_:1}),e(c,{label:"维修后图片",width:"100"},{default:t(r=>[e(y,{style:{width:"60px",height:"60px"},src:r.row.repairedUrl,"preview-src-list":[r.row.repairedUrl]},null,8,["src","preview-src-list"])]),_:1}),e(c,{prop:"description",label:"描述",align:"center",width:"300"}),e(c,{prop:"createTime",sortable:"",label:"创建时间",align:"center",width:"250"}),e(c,{prop:"updateTime",sortable:"",label:"最后更新时间",align:"center",width:"250"}),e(c,{fixed:"right",label:"操作",width:"150",align:"center"},{default:t(r=>[e(h,{link:"",type:"primary",size:"small",onClick:A=>V(r.row)},{default:t(()=>[n("查看")]),_:2},1032,["onClick"]),e(E,{title:"是否确定删除?",onConfirm:A=>P(r.row)},{reference:t(()=>[e(h,{link:"",type:"warning"},{default:t(()=>[n("永久删除")]),_:1})]),_:2},1032,["onConfirm"])]),_:1})]),_:1},8,["data"]),e(M,{"current-page":g(f).page,"onUpdate:currentPage":a[0]||(a[0]=r=>g(f).page=r),"page-size":g(f).pageSize,"onUpdate:pageSize":a[1]||(a[1]=r=>g(f).pageSize=r),"page-sizes":[10,20,40,60],layout:"total, sizes, prev, pager, next, jumper",total:g(z),onSizeChange:I,onCurrentChange:T},null,8,["current-page","page-size","total"])]),_:1}),e(W,{"align-center":"",modelValue:b.value,"onUpdate:modelValue":a[3]||(a[3]=r=>b.value=r),width:"50%"},{footer:t(()=>[X("span",te,[e(h,{onClick:a[2]||(a[2]=r=>b.value=!1)},{default:t(()=>[n("取 消")]),_:1})])]),default:t(()=>[e(x,{title:"基础信息"},{default:t(()=>[e(d,{label:"编号"},{default:t(()=>[n(s(l.id),1)]),_:1}),e(d,{label:"图片编号"},{default:t(()=>[n(s(l.id),1)]),_:1}),l.defectType?($(),Y(d,{key:0,label:"缺陷类型"},{default:t(()=>[n(s(l.id),1)]),_:1})):Z("",!0),e(d,{label:"维修时间"},{default:t(()=>[n(s(l.expectedTime),1)]),_:1}),e(d,{label:"状态"},{default:t(()=>[n(s(l.status),1)]),_:1}),e(d,{label:"创建时间"},{default:t(()=>[n(s(l.createTime),1)]),_:1}),e(d,{label:"更新时间"},{default:t(()=>[n(s(l.updateTime),1)]),_:1})]),_:1}),e(x,{title:"巡检人员"},{default:t(()=>[e(d,{label:"编号："},{default:t(()=>[n(s(l.inspectId),1)]),_:1}),e(d,{label:"姓名："},{default:t(()=>[n(s(l.inspectedName),1)]),_:1}),e(d,{label:"电话："},{default:t(()=>[n(s(l.inspectedPhone),1)]),_:1})]),_:1}),e(x,{title:"审查人员"},{default:t(()=>[e(d,{label:"编号："},{default:t(()=>[n(s(l.expertId),1)]),_:1}),e(d,{label:"姓名："},{default:t(()=>[n(s(l.expertName),1)]),_:1}),e(d,{label:"电话："},{default:t(()=>[n(s(l.expertPhone),1)]),_:1})]),_:1}),e(x,{title:"维修人员"},{default:t(()=>[e(d,{label:"编号："},{default:t(()=>[n(s(l.workerId),1)]),_:1}),e(d,{label:"姓名："},{default:t(()=>[n(s(l.workerName),1)]),_:1}),e(d,{label:"电话："},{default:t(()=>[n(s(l.workerPhone),1)]),_:1})]),_:1})]),_:1},8,["modelValue"])])}}});export{re as default};
