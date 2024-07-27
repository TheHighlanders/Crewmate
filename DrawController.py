from PIL import Image, ImageDraw, ImageFont
import math
from scipy.optimize import linear_sum_assignment
import numpy as np
import argparse


def label_controller(output_path, **custom_labels):
    controller_image = Image.open('Xbox.png')
    draw = ImageDraw.Draw(controller_image)

    default_buttons = {
        "Y": (730, 100),
        "X": (660, 170),
        "B": (800, 170),
        "A": (730, 250),
        "Start": (550, 180),
        "Back": (400, 180),
        "LB": (200, 48),
        "RB": (760, 48),
        "LT": (200, 36),
        "RT": (760, 50),
        "Left Stick": (230, 180),
        "Right Stick": (600, 330),
        "D-Up": (350, 280),
        "D-Down": (350, 390),
        "D-Left": (290, 350),
        "D-Right": (400, 350),
    }

    buttons = {k: (v, custom_labels.get(k, k)) for k, v in default_buttons.items()}

    try:
        font = ImageFont.truetype("arial.ttf", 14)
    except IOError:
        font = ImageFont.load_default()

    width, height = controller_image.size

    label_positions = []
    for i in range(5):
        x = width * (i + 1) / 6
        label_positions.append((x, 20))
        label_positions.append((x, height - 20))
    for i in range(3):
        y = height * (i + 1) / 4
        label_positions.append((20, y))
        label_positions.append((width - 20, y))

    distances = np.zeros((len(buttons), len(label_positions)))
    for i, (button_pos, _) in enumerate(buttons.values()):
        for j, label_pos in enumerate(label_positions):
            distances[i, j] = math.sqrt((button_pos[0] - label_pos[0]) ** 2 + (button_pos[1] - label_pos[1]) ** 2)

    row_ind, col_ind = linear_sum_assignment(distances)

    def get_line_end(start, end):
        vector = (end[0] - start[0], end[1] - start[1])
        length = math.sqrt(vector[0] ** 2 + vector[1] ** 2)
        unit_vector = (vector[0] / length, vector[1] / length)
        scale = length - 30
        return (int(start[0] + unit_vector[0] * scale), int(start[1] + unit_vector[1] * scale))

    for i, ((position, label), _) in enumerate(zip(buttons.values(), buttons.keys())):
        label_pos = label_positions[col_ind[i]]
        end_point = get_line_end(position, label_pos)
        draw.line([position, end_point], fill="black", width=1)
        text_pos = label_pos
        align = "left"
        if text_pos[0] < width / 2:
            align = "right"
            text_pos = (text_pos[0] + 3, text_pos[1])
        elif text_pos[0] > width / 2:
            text_pos = (text_pos[0] - 3, text_pos[1])

        if text_pos[1] < height / 2:
            text_pos = (text_pos[0], text_pos[1] + 3)
        else:
            text_pos = (text_pos[0], text_pos[1] - 17)

        for offset in [(1, 1), (-1, -1), (1, -1), (-1, 1)]:
            draw.text((text_pos[0] + offset[0], text_pos[1] + offset[1]), label, fill="white", font=font, align=align)
        draw.text(text_pos, label, fill="black", font=font, align=align)

    controller_image.save(output_path)
    print(f"Labeled image saved as '{output_path}'")



if __name__ == "__main__":
    parser = argparse.ArgumentParser(description='Label Xbox controller image with custom button names.')
    parser.add_argument('output_image', help='Path for the output labeled image')
    parser.add_argument('--custom_labels', nargs='*', metavar='BUTTON=LABEL', help='Custom button labels (e.g., Y="Arm Up")')

    args = parser.parse_args()

    custom_labels = {}
    if args.custom_labels:
        for label in args.custom_labels:
            button, label_text = label.split('=')
            custom_labels[button] = label_text

    label_controller(args.output_image, **custom_labels)



