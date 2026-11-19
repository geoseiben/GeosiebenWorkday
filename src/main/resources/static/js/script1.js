const notifications = [
    { icon:'🗓️', iconBg:'#DFF3EF', iconColor:'#12897A', title:'Leave request approved', body:'Your casual leave for Jul 24 was approved by Neha Kapoor.', time:'12m ago', unread:true },
    { icon:'🎫', iconBg:'#FBE7E4', iconColor:'#DA5B4F', title:'Ticket #TCK-4821 escalated', body:'VPN disconnect issue marked Critical and assigned to Network Team.', time:'1h ago', unread:true },
    { icon:'✅', iconBg:'#FBEEDA', iconColor:'#8A5E12', title:'Task due today', body:'“Fix cart total rounding bug” is due by end of day.', time:'3h ago', unread:true },
    { icon:'🎂', iconBg:'#F6EED9', iconColor:'#8A6F2E', title:'Upcoming birthday', body:'Priya Raman’s birthday is tomorrow — send your wishes!', time:'5h ago', unread:false },
    { icon:'📦', iconBg:'#EFF1F5', iconColor:'#495066', title:'Project milestone reached', body:'Orion Checkout Revamp crossed 70% completion.', time:'Yesterday', unread:false },
    { icon:'💻', iconBg:'#DFF3EF', iconColor:'#12897A', title:'Ticket resolved', body:'#TCK-4770 “Laptop battery replacement” was closed by IT Assets.', time:'6 days ago', unread:false },
  ];
  function notifRow(n){
    return `<div class="flex gap-3 px-4 py-3 hover:bg-mist/70 transition ${n.unread ? '' : 'opacity-60'}">
      <div class="h-8 w-8 rounded-lg flex items-center justify-center text-sm shrink-0" style="background:${n.iconBg};color:${n.iconColor}">${n.icon}</div>
      <div class="flex-1 min-w-0">
        <div class="flex items-center gap-1.5">
          <p class="text-[13px] font-medium text-ink leading-snug">${n.title}</p>
          ${n.unread ? '<span class="h-1.5 w-1.5 rounded-full bg-coral shrink-0"></span>' : ''}
        </div>
        <p class="text-xs text-ink/50 mt-0.5 leading-snug">${n.body}</p>
        <p class="text-[11px] text-ink/30 mt-1 font-mono">${n.time}</p>
      </div>
    </div>`;
  }

  const notifList = document.getElementById('notifList');
  notifList.innerHTML = notifications.map(notifRow).join('');

  const notifDot = document.getElementById('notifDot');
  const notifMarkRead = document.getElementById('notifMarkRead');

  notifMarkRead.addEventListener('click', (e) => {
    e.stopPropagation();
    notifications.forEach(n => n.unread = false);
    notifList.innerHTML = notifications.map(notifRow).join('');
    notifDot.classList.add('hidden');
  });

  // ---------- Generic dropdown system (nav menus, notifications, profile) ----------
  const dropdownTriggers = Array.from(document.querySelectorAll('[data-dropdown]'));

  function closeAllDropdowns(except){
    dropdownTriggers.forEach(btn => {
      if(btn === except) return;
      btn.classList.remove('is-active');
      const panel = document.getElementById(btn.dataset.dropdown);
      if(panel) panel.classList.add('hidden');
    });
  }

  dropdownTriggers.forEach(btn => {
    btn.addEventListener('click', (e) => {
      e.stopPropagation();
      const panel = document.getElementById(btn.dataset.dropdown);
      const willOpen = panel.classList.contains('hidden');
      closeAllDropdowns(btn);
      panel.classList.toggle('hidden', !willOpen);
      btn.classList.toggle('is-active', willOpen);
    });
  });

  document.addEventListener('click', () => closeAllDropdowns());
  document.querySelectorAll('.dropdown-panel').forEach(p => p.addEventListener('click', e => e.stopPropagation()));

  // ---------- Mobile nav toggle ----------
  const mobileNavBtn = document.getElementById('mobileNavBtn');
  const mobileNav = document.getElementById('mobileNav');
  mobileNavBtn.addEventListener('click', (e) => {
    e.stopPropagation();
    mobileNav.classList.toggle('hidden');
  });
  mobileNav.querySelectorAll('a').forEach(a => a.addEventListener('click', () => mobileNav.classList.add('hidden')));

  // ---------- Nav scroll-spy ----------
  const navLinks = document.querySelectorAll('.nav-link[data-section]');
  const sectionMap = Array.from(navLinks).map(a => ({ el:a, section:document.getElementById(a.dataset.section) }))
    .filter(x => x.section);
  function updateActiveNav(){
    const pos = window.scrollY + 140;
    let current = sectionMap[0];
    sectionMap.forEach(x => { if(x.section.offsetTop <= pos) current = x; });
    const activeSection = current ? current.section.id : null;
    document.querySelectorAll('.nav-link[data-section]').forEach(a => {
      a.classList.toggle('is-active', a.dataset.section === activeSection);
    });
  }
  window.addEventListener('scroll', updateActiveNav);
  updateActiveNav();

  // ---------- Celebrations (current month) ----------
  const celebrations = [
    { name:'Priya Raman', team:'Design', type:'Birthday', date:'Jul 16', icon:'🎂' },
    { name:'Kabir Anand', team:'Platform', type:'Work Anniversary · 3 yrs', date:'Jul 18', icon:'🎉' },
    { name:'Sara Thomas', team:'QA', type:'Birthday', date:'Jul 21', icon:'🎂' },
    { name:'Rahul Verma', team:'Sales', type:'Work Anniversary · 5 yrs', date:'Jul 24', icon:'🏆' },
    { name:'Meera Iyer', team:'Product', type:'Birthday', date:'Jul 27', icon:'🎂' },
    { name:'Divya Nair', team:'Platform', type:'Work Anniversary · 1 yr', date:'Jul 30', icon:'🎉' },
  ];

  function celebrationChip(c){
    return `<div class="flex items-center gap-2.5 px-4 shrink-0 border-r border-white/10 last:border-none">
      <span class="text-base">${c.icon}</span>
      <span class="text-xs text-white/90"><span class="font-medium text-gold-light">${c.name}</span> · ${c.type} <span class="text-white/30">(${c.date})</span></span>
    </div>`;
  }
  const track = document.getElementById('marqueeTrack');
  const chips = celebrations.map(celebrationChip).join('');
  track.innerHTML = chips + chips; // duplicate for seamless loop

  // ---------- Attendance data (last 30 days) ----------
  const attendance = [{"date": "2026-06-16", "dow": "Tue", "status": "Present", "cin": "09:00", "cout": "18:19", "hrs": 9.1}, {"date": "2026-06-17", "dow": "Wed", "status": "WFH", "cin": "09:14", "cout": "18:13", "hrs": 8.9}, {"date": "2026-06-18", "dow": "Thu", "status": "Late", "cin": "10:10", "cout": "19:13", "hrs": 7.5}, {"date": "2026-06-19", "dow": "Fri", "status": "Present", "cin": "09:11", "cout": "18:51", "hrs": 8.2}, {"date": "2026-06-20", "dow": "Sat", "status": "Weekend", "cin": "", "cout": "", "hrs": 0}, {"date": "2026-06-21", "dow": "Sun", "status": "Weekend", "cin": "", "cout": "", "hrs": 0}, {"date": "2026-06-22", "dow": "Mon", "status": "Late", "cin": "10:8", "cout": "19:18", "hrs": 7.7}, {"date": "2026-06-23", "dow": "Tue", "status": "Leave", "cin": "", "cout": "", "hrs": 0}, {"date": "2026-06-24", "dow": "Wed", "status": "Late", "cin": "10:40", "cout": "19:27", "hrs": 7.2}, {"date": "2026-06-25", "dow": "Thu", "status": "Present", "cin": "09:28", "cout": "18:34", "hrs": 8.7}, {"date": "2026-06-26", "dow": "Fri", "status": "Present", "cin": "09:09", "cout": "18:55", "hrs": 8.2}, {"date": "2026-06-27", "dow": "Sat", "status": "Weekend", "cin": "", "cout": "", "hrs": 0}, {"date": "2026-06-28", "dow": "Sun", "status": "Weekend", "cin": "", "cout": "", "hrs": 0}, {"date": "2026-06-29", "dow": "Mon", "status": "WFH", "cin": "09:15", "cout": "18:14", "hrs": 8.3}, {"date": "2026-06-30", "dow": "Tue", "status": "Present", "cin": "09:08", "cout": "18:42", "hrs": 9.0}, {"date": "2026-07-01", "dow": "Wed", "status": "Present", "cin": "09:21", "cout": "18:34", "hrs": 8.3}, {"date": "2026-07-02", "dow": "Thu", "status": "Late", "cin": "10:20", "cout": "19:2", "hrs": 7.7}, {"date": "2026-07-03", "dow": "Fri", "status": "Present", "cin": "09:24", "cout": "18:33", "hrs": 8.8}, {"date": "2026-07-04", "dow": "Sat", "status": "Weekend", "cin": "", "cout": "", "hrs": 0}, {"date": "2026-07-05", "dow": "Sun", "status": "Weekend", "cin": "", "cout": "", "hrs": 0}, {"date": "2026-07-06", "dow": "Mon", "status": "WFH", "cin": "09:14", "cout": "18:36", "hrs": 7.7}, {"date": "2026-07-07", "dow": "Tue", "status": "Present", "cin": "09:23", "cout": "18:17", "hrs": 9.3}, {"date": "2026-07-08", "dow": "Wed", "status": "WFH", "cin": "09:14", "cout": "18:30", "hrs": 8.0}, {"date": "2026-07-09", "dow": "Thu", "status": "Present", "cin": "09:07", "cout": "18:19", "hrs": 9.1}, {"date": "2026-07-10", "dow": "Fri", "status": "Present", "cin": "09:01", "cout": "18:18", "hrs": 9.0}, {"date": "2026-07-11", "dow": "Sat", "status": "Weekend", "cin": "", "cout": "", "hrs": 0}, {"date": "2026-07-12", "dow": "Sun", "status": "Weekend", "cin": "", "cout": "", "hrs": 0}, {"date": "2026-07-13", "dow": "Mon", "status": "Present", "cin": "09:24", "cout": "18:33", "hrs": 8.9}, {"date": "2026-07-14", "dow": "Tue", "status": "Present", "cin": "09:07", "cout": "18:37", "hrs": 8.2}, {"date": "2026-07-15", "dow": "Wed", "status": "WFH", "cin": "09:10", "cout": "18:23", "hrs": 8.7}];

  const statusColor = {
    Present:{ bar:'#12897A', badgeBg:'#DFF3EF', badgeText:'#12897A' },
    WFH:{ bar:'#C9A24B', badgeBg:'#F6EED9', badgeText:'#8A6F2E' },
    Late:{ bar:'#D79A34', badgeBg:'#FBEEDA', badgeText:'#8A5E12' },
    Leave:{ bar:'#DA5B4F', badgeBg:'#FBE7E4', badgeText:'#DA5B4F' },
    Weekend:{ bar:'#C7CBD3', badgeBg:'#F4F5F8', badgeText:'#8A93A1' },
  };

  function pad(t){
    if(!t) return '—';
    const [h,m] = t.split(':');
    return h.padStart(2,'0') + ':' + m.padStart(2,'0');
  }

  // stats
  const workDays = attendance.filter(d => d.status !== 'Weekend');
  const totalHours = attendance.reduce((s,d)=>s+d.hrs,0);
  const loggedDays = attendance.filter(d=>d.hrs>0).length;
  const onTimeDays = attendance.filter(d=>d.status==='Present'||d.status==='WFH').length;
  const wfhDays = attendance.filter(d=>d.status==='WFH').length;

  document.getElementById('statTotalHours').textContent = totalHours.toFixed(1)+'h';
  document.getElementById('statAvgHours').textContent = (totalHours/loggedDays).toFixed(1)+'h';
  document.getElementById('statOnTime').textContent = Math.round((onTimeDays/workDays.length)*100)+'%';
  document.getElementById('statWfh').textContent = wfhDays+' days';

  // tile grid
  const grid = document.getElementById('attendanceGrid');
  grid.innerHTML = attendance.map(d=>{
    const c = statusColor[d.status];
    const dateLabel = new Date(d.date+'T00:00:00').toLocaleDateString('en-GB',{ day:'2-digit', month:'short' });
    const tooltip = d.status==='Weekend' ? 'Weekend'
      : d.status==='Leave' ? 'On Leave'
      : `In ${pad(d.cin)} · Out ${pad(d.cout)}`;
    const isToday = d.date === attendance[attendance.length-1].date;
    return `<div title="${tooltip}"
        class="group relative rounded-xl border px-2.5 py-3 flex flex-col items-center text-center transition hover:-translate-y-0.5 hover:shadow-md ${isToday ? 'ring-2 ring-gold' : ''}"
        style="background:${c.badgeBg};border-color:${c.badgeText}22">
      <span class="text-[10px] uppercase tracking-wider font-medium" style="color:${c.badgeText}">${d.dow}</span>
      <span class="font-display text-[13px] text-ink/70 mt-0.5">${dateLabel}</span>
      <span class="font-mono text-base font-semibold mt-1.5" style="color:${d.hrs>0 ? '#0B1220' : c.badgeText}">${d.hrs>0 ? d.hrs.toFixed(1) : '—'}</span>
      <span class="text-[9px] text-ink/30 -mt-0.5">${d.hrs>0 ? 'hrs' : d.status}</span>
      <span class="mt-1.5 h-1.5 w-1.5 rounded-full" style="background:${c.badgeText}"></span>
    </div>`;
  }).join('');