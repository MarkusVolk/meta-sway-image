LICENSE = "GPL-2.0-only"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/GPL-2.0-only;md5=801f80980d171dd6425610833a22dbe6"

inherit main-user systemd

SRC_URI = " \
	file://bashrc \
	file://bash_profile \
	file://flathub.sh \
	file://flathub.service \
	file://system-auth \
	file://sway/config \
	file://foot/foot.ini \
	file://swappy/config \
	file://waybar/style.css \
	file://waybar/config \
	file://nwg-launchers/nwgbar/bar.json \
	file://nwg-launchers/nwggrid/terminal \
	file://nwg-launchers/nwggrid/grid.conf \
	file://nwg-look/gsettings \
	file://online-accounts.desktop \
"

do_install() {
	install -d ${D}${systemd_user_unitdir}/default.target.wants ${D}${bindir} ${D}${MAIN_USER_HOMEDIR}
	install -d ${D}${sysconfdir}/pam.d ${D}${MAIN_USER_HOMEDIR}/.config/sway
	install -d ${D}${datadir}/applications
	install -d ${D}${MAIN_USER_HOMEDIR}/.config/foot ${D}${MAIN_USER_HOMEDIR}/.config/swappy
	install -d ${D}${MAIN_USER_HOMEDIR}/.config/waybar ${D}${MAIN_USER_HOMEDIR}/.local/share/nwg-look
	install -d ${D}${MAIN_USER_HOMEDIR}/.config/nwg-launchers/nwgbar
	install -d ${D}${MAIN_USER_HOMEDIR}/.config/nwg-launchers/nwggrid ${D}${MAIN_USER_HOMEDIR}/.config/sirula
	install -m 0644 ${WORKDIR}/flathub.service ${D}${systemd_user_unitdir}
	install -m 0755 ${WORKDIR}/flathub.sh ${D}${bindir}/flathub.sh
	install -m 0644 ${WORKDIR}/bash_profile ${D}${MAIN_USER_HOMEDIR}/.bash_profile
	install -m 0644 ${WORKDIR}/bashrc ${D}${MAIN_USER_HOMEDIR}/.bashrc
	install -m 0644 ${WORKDIR}/sway/config ${D}${MAIN_USER_HOMEDIR}/.config/sway
	install -m 0644 ${WORKDIR}/foot/foot.ini ${D}${MAIN_USER_HOMEDIR}/.config/foot
	install -m 0644 ${WORKDIR}/swappy/config ${D}${MAIN_USER_HOMEDIR}/.config/swappy
	install -m 0644 ${WORKDIR}/waybar/config ${D}${MAIN_USER_HOMEDIR}/.config/waybar
	install -m 0644 ${WORKDIR}/waybar/style.css ${D}${MAIN_USER_HOMEDIR}/.config/waybar
	install -m 0644 ${WORKDIR}/nwg-launchers/nwgbar/bar.json ${D}${MAIN_USER_HOMEDIR}/.config/nwg-launchers/nwgbar
	install -m 0644 ${WORKDIR}/nwg-launchers/nwggrid/grid.conf ${D}${MAIN_USER_HOMEDIR}/.config/nwg-launchers/nwggrid
	install -m 0644 ${WORKDIR}/nwg-launchers/nwggrid/terminal ${D}${MAIN_USER_HOMEDIR}/.config/nwg-launchers/nwggrid
	install -m 0644 ${WORKDIR}/nwg-look/gsettings ${D}${MAIN_USER_HOMEDIR}/.local/share/nwg-look
	install -m 0644 ${WORKDIR}/system-auth ${D}${sysconfdir}/pam.d
	install -m 0644 ${WORKDIR}/online-accounts.desktop ${D}${datadir}/applications
	echo "XDG_CURRENT_DESKTOP=GNOME gnome-control-center online-accounts" > ${D}${bindir}/online-accounts
	chmod +x ${D}${bindir}/online-accounts
	ln -fs ${systemd_user_unitdir}/flathub.service ${D}${systemd_user_unitdir}/default.target.wants
	touch ${D}${MAIN_USER_HOMEDIR}/.config/sway/outputs
	chown ${MAIN_USER_NAME}:${MAIN_USER_NAME} -R ${D}${MAIN_USER_HOMEDIR}
}

PACKAGES += "${PN}-flathub"

FILES:${PN} = "${MAIN_USER_HOMEDIR} ${sysconfdir} ${datadir} ${bindir}"
FILES:${PN}-flathub = "${bindir}/flathub.sh ${systemd_user_unitdir}"
