import{f as G,h as H,i as J,j as K}from"./index-CWz7Ae3v.js";import{b as O,r as u,a as b,o as Q,c as j,q as e,m as t,k as X,n as l,i as h,g as p,F as Y,x as Z,l as I,e as r,t as S,y as ee,z as te,p as ae,j as ne,_ as le}from"./index-kk0mXoR3.js";const f=m=>(ae("data-v-6c197bd7"),m=m(),ne(),m),oe=f(()=>r("div",{class:"el-upload__text"},[p(" 拖拽文件上传或 "),r("em",null,"点击此处上传")],-1)),se=f(()=>r("div",{class:"el-upload__tip"},"jpg/png格式",-1)),ce=f(()=>r("span",null,"拍摄设备：",-1)),ue=f(()=>r("b",null,"类型:",-1)),re=f(()=>r("b",null,"风险级别:",-1)),de=O({__name:"Detecting",setup(m){const g=u("");u(!1),u(""),u(!1);const V=b([]),L=u(),y=u(),$=u(!1),{proxy:a}=X(),v=u(""),_=b({description:"",instrument:"",picId:""}),d=b({type:"",url:"",rank:""}),w=b([]);async function C(n){if(n.code!==1)return a.$message.error("上传失败");v.value=n.data,console.log(v.value),a.$startLoading();const o=await H(n.data);if(a.$closeLoading(),o.code!==1)return a.$message.error("上传失败");d.type=o.data[0],d.url=o.data[1],d.rank=o.data[2],$.value=!0}function z(n){y.value.clearFiles();const o=n[0];o.uid=te(),y.value.handleStart(o)}function U(n){L.value=n}async function F(){a.$startLoading();const n=await J(v.value);if(a.$closeLoading(),n.code!==1)return a.$message.error("服务器故障");a.$message.success("会审工单生成，待专家会审"),$.value=!1}async function B(){if(!L.value||_.instrument==="")return a.$message.error("请填写完整信息");g.value=a.$baseUrl+"/picture/upload?"+new URLSearchParams(_),console.log(g.value),y.value.submit()}async function D(){a.$startLoading();const n=await K(v.value);if(a.$closeLoading(),n!==200)return a.$message.error("下载失败")}return Q(async()=>{g.value=a.$baseUrl+"/picture/upload",a.$startLoading();const n=await G({page:"1",pageSize:"1000",name:""});if(a.$closeLoading(),n.code!==1)return a.$message.error("服务器故障");w.splice(0,w.length,...n.data.records)}),(n,o)=>{const k=l("el-breadcrumb-item"),N=l("el-breadcrumb"),A=l("upload-filled"),E=l("el-icon"),R=l("el-upload"),s=l("el-col"),q=l("el-option"),M=l("el-select"),c=l("el-row"),P=l("el-input"),x=l("el-button"),T=l("el-image"),W=l("el-card");return h(),j("div",null,[e(N,{separator:"/"},{default:t(()=>[e(k,{to:{path:"/home"}},{default:t(()=>[p("首页")]),_:1}),e(k,null,{default:t(()=>[p("缺陷检测管理")]),_:1}),e(k,null,{default:t(()=>[p("缺陷检测")]),_:1})]),_:1}),e(W,null,{default:t(()=>[e(c,{gutter:100},{default:t(()=>[e(s,{span:12,class:"content-container"},{default:t(()=>[e(R,{class:"upload-demo",action:g.value,"file-list":V,drag:"",limit:1,"list-type":"picture","on-success":C,"auto-upload":!1,"on-exceed":z,"on-change":U,accept:"image/png, image/jpeg",ref_key:"upload",ref:y},{tip:t(()=>[se]),default:t(()=>[e(E,{class:"el-icon--upload"},{default:t(()=>[e(A)]),_:1}),oe]),_:1},8,["action","file-list"]),e(c,{justify:"start",gutter:20},{default:t(()=>[e(s,{span:5},{default:t(()=>[ce]),_:1}),e(s,{span:5},{default:t(()=>[e(M,{modelValue:_.instrument,"onUpdate:modelValue":o[0]||(o[0]=i=>_.instrument=i),placeholder:"Select",style:{width:"240px"}},{default:t(()=>[(h(!0),j(Y,null,Z(w,i=>(h(),I(q,{key:i.id,label:i.name,value:i.name},null,8,["label","value"]))),128))]),_:1},8,["modelValue"])]),_:1})]),_:1}),e(c,null,{default:t(()=>[e(P,{modelValue:_.description,"onUpdate:modelValue":o[1]||(o[1]=i=>_.description=i),maxlength:"100",placeholder:"描述","show-word-limit":"",type:"textarea"},null,8,["modelValue"])]),_:1}),e(c,{justify:"center"},{default:t(()=>[e(x,{class:"submit-button",type:"primary",onClick:B},{default:t(()=>[p("上传")]),_:1})]),_:1})]),_:1}),$.value?(h(),I(s,{key:0,span:12,class:"content-container"},{default:t(()=>[e(c,{justify:"center"},{default:t(()=>[e(s,{span:12},{default:t(()=>[e(T,{src:d.url,"preview-src-list":[d.url],fit:"cover"},null,8,["src","preview-src-list"])]),_:1})]),_:1}),e(c,{justify:"center"},{default:t(()=>[e(s,{span:3},{default:t(()=>[ue]),_:1}),e(s,{span:3},{default:t(()=>[r("span",null,S(d.type),1)]),_:1})]),_:1}),e(c,{justify:"center"},{default:t(()=>[e(s,{span:3},{default:t(()=>[re]),_:1}),e(s,{span:3},{default:t(()=>[r("span",null,S(d.rank),1)]),_:1})]),_:1}),e(c,{justify:"center"},{default:t(()=>[e(x,{onClick:D},{default:t(()=>[p("下载")]),_:1})]),_:1}),e(c,{justify:"center"},{default:t(()=>[e(x,{class:"submit-button",onClick:F,type:"primary"},{default:t(()=>[p("确定并提交")]),_:1})]),_:1})]),_:1})):ee("",!0)]),_:1})]),_:1})])}}}),_e=le(de,[["__scopeId","data-v-6c197bd7"]]);export{_e as default};
